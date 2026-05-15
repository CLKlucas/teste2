package br.com.topcar.rmi.codec;

import br.com.topcar.model.ItemPedido;
import br.com.topcar.model.Peca;
import br.com.topcar.model.Pedido;
import br.com.topcar.model.storage.Amortecedor;
import br.com.topcar.model.storage.Bateria;
import br.com.topcar.model.storage.Farol;
import br.com.topcar.model.storage.Motor;
import br.com.topcar.model.storage.Pneu;
import br.com.topcar.model.user.Cliente;
import br.com.topcar.model.utils.Car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ConversorTopcarJson {
    private ConversorTopcarJson() {
    }

    public static String pecasParaJson(Collection<Peca> pecas) {
        List<String> values = new ArrayList<>();
        for (Peca peca : pecas) {
            values.add(pecaParaJson(peca));
        }
        return ArquivoJson.objeto(ArquivoJson.campoJson("pecas", ArquivoJson.lista(values)));
    }

    public static String pecaParaJson(Peca peca) {
        List<String> fields = new ArrayList<>();
        fields.add(ArquivoJson.campoNumero("id", peca.getId()));
        fields.add(ArquivoJson.campoTexto("classe", peca.getClass().getSimpleName()));
        fields.add(ArquivoJson.campoTexto("nome", peca.getNome()));
        fields.add(ArquivoJson.campoTexto("valor", peca.getValor().toPlainString()));
        fields.add(ArquivoJson.campoTexto("dataFabricacao", peca.getData_fabricacao().toString()));
        fields.add(ArquivoJson.campoJson("carro", carroParaJson(peca.getCarro())));

        if (peca instanceof Pneu pneu) {
            fields.add(ArquivoJson.campoTexto("dimensoes", pneu.getDimensoes()));
            fields.add(ArquivoJson.campoTexto("indiceTraction", pneu.getIndiceTraction()));
        } else if (peca instanceof Motor motor) {
            fields.add(ArquivoJson.campoNumero("potenciaCv", motor.getPotenciaCv()));
            fields.add(ArquivoJson.campoNumero("torqueKgf", motor.getTorqueKgf()));
        } else if (peca instanceof Bateria bateria) {
            fields.add(ArquivoJson.campoNumero("cca", bateria.getCca()));
            fields.add(ArquivoJson.campoTexto("tipoQuimica", bateria.getTipoQuimica()));
        } else if (peca instanceof Farol farol) {
            fields.add(ArquivoJson.campoTexto("tipo", farol.getTipo()));
            fields.add(ArquivoJson.campoBooleano("led", farol.isLed()));
        } else if (peca instanceof Amortecedor amortecedor) {
            fields.add(ArquivoJson.campoTexto("tipo", amortecedor.getTipo()));
            fields.add(ArquivoJson.campoTexto("posicao", amortecedor.getPosicao()));
        }

        return ArquivoJson.objeto(fields.toArray(new String[0]));
    }

    public static String clienteParaJson(Cliente cliente) {
        List<String> pedidos = new ArrayList<>();
        for (Pedido pedido : cliente.getPedidos()) {
            pedidos.add(pedidoParaJson(pedido));
        }

        return ArquivoJson.objeto(
                ArquivoJson.campoTexto("cpf", cliente.getCpf()),
                ArquivoJson.campoTexto("nome", cliente.getNome()),
                ArquivoJson.campoNumero("idade", cliente.getIdade()),
                ArquivoJson.campoJson("pedidos", ArquivoJson.lista(pedidos)));
    }

    public static Cliente clienteDoJson(String json) {
        return new Cliente(
                ArquivoJson.valorTexto(json, "cpf"),
                ArquivoJson.valorTexto(json, "nome"),
                ArquivoJson.valorInteiro(json, "idade"),
                new ArrayList<>());
    }

    public static String pedidoParaJson(Pedido pedido) {
        List<String> itens = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            itens.add(itemPedidoParaJson(item));
        }

        return ArquivoJson.objeto(
                ArquivoJson.campoNumero("id", pedido.getId()),
                ArquivoJson.campoJson("itens", ArquivoJson.lista(itens)));
    }

    public static String totalParaJson(int pedidoId, BigDecimal total) {
        return ArquivoJson.objeto(
                ArquivoJson.campoNumero("pedidoId", pedidoId),
                ArquivoJson.campoTexto("total", total.toPlainString()));
    }

    public static int idDoJson(String json) {
        return ArquivoJson.valorInteiro(json, "id");
    }

    public static int idPedidoDoJson(String json) {
        return ArquivoJson.valorInteiro(json, "pedidoId");
    }

    public static ArgumentosPedido argumentosPedidoDoJson(String json) {
        String cpf = ArquivoJson.valorTexto(json, "cpf");
        String itensJson = ArquivoJson.valorBruto(json, "itens");
        if (itensJson == null) {
            throw new IllegalArgumentException("Campo obrigatorio ausente: itens");
        }

        List<ArgumentosItemPedido> itens = new ArrayList<>();
        for (String itemJson : ArquivoJson.separarItens(itensJson)) {
            itens.add(new ArgumentosItemPedido(
                    ArquivoJson.valorInteiro(itemJson, "pecaId"),
                    ArquivoJson.valorInteiro(itemJson, "quantidade"),
                    ArquivoJson.valorDecimal(itemJson, "descontoPercentual", BigDecimal.ZERO)));
        }
        return new ArgumentosPedido(cpf, itens);
    }

    public static String pedidoCriadoParaJson(Pedido pedido, BigDecimal total) {
        return ArquivoJson.objeto(
                ArquivoJson.campoJson("pedido", pedidoParaJson(pedido)),
                ArquivoJson.campoTexto("total", total.toPlainString()));
    }

    private static String carroParaJson(Car car) {
        return ArquivoJson.objeto(
                ArquivoJson.campoTexto("nome", car.getNome()),
                ArquivoJson.campoNumero("ano", car.getAno()),
                ArquivoJson.campoTexto("modelo", car.getModelo()));
    }

    private static String itemPedidoParaJson(ItemPedido item) {
        return ArquivoJson.objeto(
                ArquivoJson.campoJson("peca", pecaParaJson(item.getPeca())),
                ArquivoJson.campoNumero("quantidade", item.getQuantidade()),
                ArquivoJson.campoTexto("valorTotal", item.getValorTotal().toPlainString()),
                ArquivoJson.campoTexto("desconto", item.getDesconto().toPlainString()));
    }

    public static final class ArgumentosPedido {
        private final String cpf;
        private final List<ArgumentosItemPedido> itens;

        public ArgumentosPedido(String cpf, List<ArgumentosItemPedido> itens) {
            this.cpf = cpf;
            this.itens = itens;
        }

        public String getCpf() {
            return cpf;
        }

        public List<ArgumentosItemPedido> getItens() {
            return itens;
        }
    }

    public static final class ArgumentosItemPedido {
        private final int pecaId;
        private final int quantidade;
        private final BigDecimal descontoPercentual;

        public ArgumentosItemPedido(int pecaId, int quantidade, BigDecimal descontoPercentual) {
            this.pecaId = pecaId;
            this.quantidade = quantidade;
            this.descontoPercentual = descontoPercentual;
        }

        public int getPecaId() {
            return pecaId;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public BigDecimal getDescontoPercentual() {
            return descontoPercentual;
        }
    }
}
