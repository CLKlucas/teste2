package br.com.topcar.rmi.server;

import br.com.topcar.rmi.codec.ConversorMensagemJson;
import br.com.topcar.rmi.protocol.RetornoCliente;
import br.com.topcar.rmi.protocol.MensagemResposta;
import br.com.topcar.rmi.protocol.MensagemRequisicao;
import br.com.topcar.rmi.protocol.RequisicaoRespostaRemota;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TratadorRequisicaoTopcar extends UnicastRemoteObject implements RequisicaoRespostaRemota {
    private final ServicoCatalogoTopcar catalogService;

    public TratadorRequisicaoTopcar(ServicoCatalogoTopcar catalogService) throws RemoteException {
        this.catalogService = catalogService;
    }

    @Override
    public byte[] getRequest(byte[] request, RetornoCliente callback) throws RemoteException {
        MensagemRequisicao requestMessage = null;
        MensagemResposta reply;

        try {
            requestMessage = ConversorMensagemJson.decodificarRequisicao(request);
            String resultJson = catalogService.execute(
                    requestMessage.getObjectReference(),
                    requestMessage.getMethodId(),
                    requestMessage.getArgumentsJson());
            reply = new MensagemResposta(requestMessage.getRequestId(), true, "Operacao executada", resultJson);
        } catch (RuntimeException e) {
            String requestId = "request-invalida";
            if (requestMessage != null) {
                requestId = requestMessage.getRequestId();
            }
            reply = new MensagemResposta(requestId, false, e.getMessage(), "{}");
        }

        byte[] replyBytes = ConversorMensagemJson.codificarResposta(reply);
        notifyCallback(callback, reply.getRequestId(), replyBytes);
        return sendReply(replyBytes, "rmi-client", 0);
    }

    public byte[] sendReply(byte[] reply, String clientHost, int clientPort) {
        return reply;
    }

    private void notifyCallback(RetornoCliente callback, String requestId, byte[] replyBytes) {
        if (callback == null) {
            return;
        }

        try {
            callback.receiveReplyNotice(requestId, replyBytes);
        } catch (RemoteException e) {
            System.out.println("Callback do cliente falhou: " + e.getMessage());
        }
    }
}
