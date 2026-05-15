package br.com.topcar.rmi.server;

import br.com.topcar.model.ItemPedido;
import br.com.topcar.model.Peca;
import br.com.topcar.model.Pedido;
import br.com.topcar.model.storage.Bateria;
import br.com.topcar.model.storage.Farol;
import br.com.topcar.model.storage.Motor;
import br.com.topcar.model.storage.Pneu;
import br.com.topcar.model.user.Cliente;
import br.com.topcar.model.utils.Car;
import br.com.topcar.rmi.codec.ArquivoJson;
import br.com.topcar.rmi.codec.ConversorTopcarJson;
import br.com.topcar.rmi.protocol.ReferenciaObjetoRemoto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicoCatalogoTopcar {
    private final Map<Integer, Peca> estoque = new LinkedHashMap<>();
    private final Map<String, Cliente> clientes = new LinkedHashMap<>();
    private final Map<Integer, Pedido> pedidos = new LinkedHashMap<>();
    private final AtomicInteger pedidoSequence = new AtomicInteger(1001);

    public ServicoCatalogoTopcar() {
        carregarEstoqueInicial();
    }

    public String execute(String objectReference, String methodId, String argumentsJson) {
        if (!ReferenciaObjetoRemoto.DEFAULT_OBJECT_NAME.equals(objectReference)) {
            throw new IllegalArgumentException("Referencia remota desconhecida: " + objectReference);
        }

        return switch (methodId.toUpperCase(Locale.ROOT)) {
            case "LISTAR_PECAS" -> listarPecas();
            case "BUSCAR_PECA" -> buscarPeca(argumentsJson);
            case "CADASTRAR_CLIENTE" -> cadastrarCliente(argumentsJson);
            case "CRIAR_PEDIDO" -> criarPedido(argumentsJson);
            case "CALCULAR_TOTAL_PEDIDO" -> calcularTotalPedido(argumentsJson);
            case "CONSULTAR_CLIENTE" -> consultarCliente(argumentsJson);
            case "LISTAR_OPERACOES" -> listarOperacoes();
            default -> throw new IllegalArgumentException("Metodo remoto nao suportado: " + methodId);
        };
    }

    private String listarPecas() {
        return ConversorTopcarJson.pecasParaJson(estoque.values());
    }

    private String buscarPeca(String argumentsJson) {
        int id = ConversorTopcarJson.idDoJson(argumentsJson);
        Peca peca = estoque.get(id);
        if (peca == null) {
            throw new IllegalArgumentException("Peca nao encontrada: " + id);
        }
        return ArquivoJson.objeto(ArquivoJson.campoJson("peca", ConversorTopcarJson.pecaParaJson(peca)));
    }

    private String cadastrarCliente(String argumentsJson) {
        Cliente cliente = ConversorTopcarJson.clienteDoJson(argumentsJson);
        clientes.put(cliente.getCpf(), cliente);
        return ArquivoJson.objeto(ArquivoJson.campoJson("cliente", ConversorTopcarJson.clienteParaJson(cliente)));
    }

    private String criarPedido(String argumentsJson) {
        ConversorTopcarJson.ArgumentosPedido order = ConversorTopcarJson.argumentosPedidoDoJson(argumentsJson);
        Cliente cliente = clientes.get(order.getCpf());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao cadastrado: " + order.getCpf());
        }
        if (order.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido precisa ter pelo menos um item");
        }

        List<ItemPedido> itens = new ArrayList<>();
        for (ConversorTopcarJson.ArgumentosItemPedido itemArguments : order.getItens()) {
            Peca peca = estoque.get(itemArguments.getPecaId());
            if (peca == null) {
                throw new IllegalArgumentException("Peca nao encontrada: " + itemArguments.getPecaId());
            }
            if (itemArguments.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade precisa ser positiva");
            }

            ItemPedido item = new ItemPedido(peca, itemArguments.getQuantidade());
            aplicarDescontoSeInformado(item, itemArguments.getDescontoPercentual());
            itens.add(item);
        }

        Pedido pedido = new Pedido(pedidoSequence.getAndIncrement(), itens);
        pedidos.put(pedido.getId(), pedido);
        cliente.efetuarPedido(pedido);
        return ConversorTopcarJson.pedidoCriadoParaJson(pedido, totalPedido(pedido));
    }

    private String calcularTotalPedido(String argumentsJson) {
        int pedidoId = ConversorTopcarJson.idPedidoDoJson(argumentsJson);
        Pedido pedido = pedidos.get(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao encontrado: " + pedidoId);
        }
        return ConversorTopcarJson.totalParaJson(pedidoId, totalPedido(pedido));
    }

    private String consultarCliente(String argumentsJson) {
        String cpf = ArquivoJson.valorTexto(argumentsJson, "cpf");
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao encontrado: " + cpf);
        }
        return ArquivoJson.objeto(ArquivoJson.campoJson("cliente", ConversorTopcarJson.clienteParaJson(cliente)));
    }

    private String listarOperacoes() {
        List<String> operacoes = List.of(
                ArquivoJson.textoJson("LISTAR_PECAS"),
                ArquivoJson.textoJson("BUSCAR_PECA"),
                ArquivoJson.textoJson("CADASTRAR_CLIENTE"),
                ArquivoJson.textoJson("CRIAR_PEDIDO"),
                ArquivoJson.textoJson("CALCULAR_TOTAL_PEDIDO"),
                ArquivoJson.textoJson("CONSULTAR_CLIENTE"));
        return ArquivoJson.objeto(ArquivoJson.campoJson("operacoes", ArquivoJson.lista(operacoes)));
    }

    private void aplicarDescontoSeInformado(ItemPedido item, BigDecimal descontoPercentual) {
        if (descontoPercentual.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        BigDecimal valorComDesconto = item.aplicarDesconto(descontoPercentual);
        BigDecimal descontoUnitario = item.getPeca().getValor().subtract(valorComDesconto);
        item.setDesconto(descontoUnitario.multiply(BigDecimal.valueOf(item.getQuantidade())));
        item.setValorTotal(valorComDesconto.multiply(BigDecimal.valueOf(item.getQuantidade())));
    }

    private BigDecimal totalPedido(Pedido pedido) {
        return pedido.getItens().stream()
                .map(ItemPedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void carregarEstoqueInicial() {
        Car corolla = new Car("Toyota Corolla", 2020, "XEi");
        Car civic = new Car("Honda Civic", 2019, "EXL");
        Car onix = new Car("Chevrolet Onix", 2022, "Premier");

        estoque.put(1, new Pneu(
                1,
                "Pneu aro 16",
                new BigDecimal("420.00"),
                LocalDate.of(2025, 1, 15),
                corolla,
                "205/55R16",
                "A"));
        estoque.put(2, new Motor(
                2,
                "Motor 1.8 flex",
                new BigDecimal("8500.00"),
                LocalDate.of(2024, 11, 4),
                corolla,
                144,
                17.5f));
        estoque.put(3, new Bateria(
                3,
                "Bateria 60Ah",
                new BigDecimal("530.00"),
                LocalDate.of(2025, 2, 20),
                civic,
                430,
                "chumbo-acido"));
        estoque.put(4, new Farol(
                4,
                "Farol LED esquerdo",
                new BigDecimal("680.00"),
                LocalDate.of(2024, 8, 9),
                onix,
                "baixo",
                true));
    }
}
