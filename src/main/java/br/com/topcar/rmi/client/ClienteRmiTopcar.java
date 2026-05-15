package br.com.topcar.rmi.client;

import br.com.topcar.rmi.codec.ArquivoJson;
import br.com.topcar.rmi.codec.ConversorMensagemJson;
import br.com.topcar.rmi.protocol.ReferenciaObjetoRemoto;
import br.com.topcar.rmi.protocol.MensagemResposta;
import br.com.topcar.rmi.protocol.RequisicaoRespostaRemota;
import br.com.topcar.rmi.protocol.Requisitante;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClienteRmiTopcar {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }

        int port = ReferenciaObjetoRemoto.DEFAULT_REGISTRY_PORT;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        String bindingName = ReferenciaObjetoRemoto.DEFAULT_BINDING_NAME;
        if (args.length > 2) {
            bindingName = args[2];
        }

        Registry registry = LocateRegistry.getRegistry(host, port);
        RequisicaoRespostaRemota remote = (RequisicaoRespostaRemota) registry.lookup(bindingName);
        RetornoClienteConsole callback = new RetornoClienteConsole();
        Requisitante requestor = new Requisitante(remote, callback);
        ReferenciaObjetoRemoto objectRef = new ReferenciaObjetoRemoto(
                ReferenciaObjetoRemoto.DEFAULT_OBJECT_NAME,
                host,
                port,
                bindingName);

        try {
            rodarDemonstracao(requestor, objectRef);
        } finally {
            UnicastRemoteObject.unexportObject(callback, true);
        }
    }

    private static void rodarDemonstracao(Requisitante requestor, ReferenciaObjetoRemoto objectRef) throws Exception {
        imprimirResposta("LISTAR_PECAS", requestor.chamar(objectRef, "LISTAR_PECAS", "{}"));
        imprimirResposta("BUSCAR_PECA", requestor.chamar(objectRef, "BUSCAR_PECA", "{\"id\":2}"));
        imprimirResposta("CADASTRAR_CLIENTE", requestor.chamar(
                objectRef,
                "CADASTRAR_CLIENTE",
                "{\"cpf\":\"123.456.789-00\",\"nome\":\"Lucas\",\"idade\":22}"));
        imprimirResposta("CRIAR_PEDIDO", requestor.chamar(
                objectRef,
                "CRIAR_PEDIDO",
                "{\"cpf\":\"123.456.789-00\",\"itens\":["
                        + "{\"pecaId\":1,\"quantidade\":4,\"descontoPercentual\":5},"
                        + "{\"pecaId\":3,\"quantidade\":1}"
                        + "]}"));
        imprimirResposta("CALCULAR_TOTAL_PEDIDO", requestor.chamar(
                objectRef,
                "CALCULAR_TOTAL_PEDIDO",
                "{\"pedidoId\":1001}"));
        imprimirResposta("CONSULTAR_CLIENTE", requestor.chamar(
                objectRef,
                "CONSULTAR_CLIENTE",
                "{\"cpf\":\"123.456.789-00\"}"));

        byte[] replyByNumericMethod = requestor.doOperation(objectRef, 1, ArquivoJson.paraBytes("{}"));
        imprimirResposta("METODO_NUMERICO_1", ConversorMensagemJson.decodificarResposta(replyByNumericMethod));
    }

    private static void imprimirResposta(String operation, MensagemResposta reply) {
        System.out.println();
        System.out.println("== " + operation + " ==");
        System.out.println("Sucesso: " + reply.isSuccess());
        System.out.println("Mensagem: " + reply.getMessage());
        System.out.println("Resultado JSON: " + reply.getResultJson());
    }
}
