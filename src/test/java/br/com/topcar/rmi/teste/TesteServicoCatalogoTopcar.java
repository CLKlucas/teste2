package test.br.com.topcar.rmi.teste;

import br.com.topcar.rmi.codec.ArquivoJson;
import br.com.topcar.rmi.protocol.ReferenciaObjetoRemoto;
import br.com.topcar.rmi.server.ServicoCatalogoTopcar;

import java.util.List;

public class TesteServicoCatalogoTopcar {
    public static void main(String[] args) {
        ServicoCatalogoTopcar servico = new ServicoCatalogoTopcar();

        testarListagem(servico);
        testarBusca(servico);
        testarCadastroCliente(servico);
        testarPedido(servico);

        System.out.println("TesteServicoCatalogoTopcar finalizado com sucesso.");
    }

    private static void testarListagem(ServicoCatalogoTopcar servico) {
        String resposta = executar(servico, "LISTAR_PECAS", "{}");
        verificar(resposta.contains("\"Pneu aro 16\""), "Listagem nao trouxe o pneu");
        verificar(resposta.contains("\"Motor 1.8 flex\""), "Listagem nao trouxe o motor");
    }

    private static void testarBusca(ServicoCatalogoTopcar servico) {
        String resposta = executar(servico, "BUSCAR_PECA", "{\"id\":2}");
        verificar(resposta.contains("\"id\":2"), "Busca nao trouxe a peca de ID 2");
        verificar(resposta.contains("\"Motor\""), "Busca nao trouxe o tipo Motor");
    }

    private static void testarCadastroCliente(ServicoCatalogoTopcar servico) {
        String clienteJson = ArquivoJson.objeto(
                ArquivoJson.campoTexto("cpf", "123.456.789-00"),
                ArquivoJson.campoTexto("nome", "Lucas"),
                ArquivoJson.campoNumero("idade", 22));

        String resposta = executar(servico, "CADASTRAR_CLIENTE", clienteJson);
        verificar(resposta.contains("\"nome\":\"Lucas\""), "Cliente nao foi cadastrado corretamente");
    }

    private static void testarPedido(ServicoCatalogoTopcar servico) {
        String item1 = ArquivoJson.objeto(
                ArquivoJson.campoNumero("pecaId", 1),
                ArquivoJson.campoNumero("quantidade", 4),
                ArquivoJson.campoNumero("descontoPercentual", 5));
        String item2 = ArquivoJson.objeto(
                ArquivoJson.campoNumero("pecaId", 3),
                ArquivoJson.campoNumero("quantidade", 1));

        String pedidoJson = ArquivoJson.objeto(
                ArquivoJson.campoTexto("cpf", "123.456.789-00"),
                ArquivoJson.campoJson("itens", ArquivoJson.lista(List.of(item1, item2))));

        String respostaPedido = executar(servico, "CRIAR_PEDIDO", pedidoJson);
        verificar(respostaPedido.contains("\"id\":1001"), "Pedido nao foi criado com ID esperado");
        verificar(respostaPedido.contains("\"total\":\"2126.0000\""), "Total do pedido criado esta errado");

        String respostaTotal = executar(servico, "CALCULAR_TOTAL_PEDIDO", "{\"pedidoId\":1001}");
        verificar(respostaTotal.contains("\"total\":\"2126.0000\""), "Total do pedido consultado esta errado");

        String respostaCliente = executar(servico, "CONSULTAR_CLIENTE", "{\"cpf\":\"123.456.789-00\"}");
        verificar(respostaCliente.contains("\"pedidos\""), "Cliente consultado nao trouxe pedidos");
        verificar(respostaCliente.contains("\"id\":1001"), "Cliente consultado nao trouxe o pedido criado");
    }

    private static String executar(ServicoCatalogoTopcar servico, String metodo, String argumentos) {
        return servico.execute(ReferenciaObjetoRemoto.DEFAULT_OBJECT_NAME, metodo, argumentos);
    }

    private static void verificar(boolean condicao, String mensagemErro) {
        if (!condicao) {
            throw new IllegalStateException(mensagemErro);
        }
    }
}
