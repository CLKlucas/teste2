package br.com.topcar.rmi.codec;

import br.com.topcar.rmi.protocol.MensagemResposta;
import br.com.topcar.rmi.protocol.MensagemRequisicao;

public final class ConversorMensagemJson {
    private ConversorMensagemJson() {
    }

    public static byte[] codificarRequisicao(MensagemRequisicao request) {
        String json = ArquivoJson.objeto(
                ArquivoJson.campoTexto("messageType", "request"),
                ArquivoJson.campoTexto("requestId", request.getRequestId()),
                ArquivoJson.campoTexto("objectReference", request.getObjectReference()),
                ArquivoJson.campoTexto("methodId", request.getMethodId()),
                ArquivoJson.campoJson("arguments", request.getArgumentsJson()));
        return ArquivoJson.paraBytes(json);
    }

    public static MensagemRequisicao decodificarRequisicao(byte[] requestBytes) {
        String json = ArquivoJson.deBytes(requestBytes);
        return new MensagemRequisicao(
                ArquivoJson.valorTexto(json, "requestId"),
                ArquivoJson.valorTexto(json, "objectReference"),
                ArquivoJson.valorTexto(json, "methodId"),
                ArquivoJson.valorBruto(json, "arguments"));
    }

    public static byte[] codificarResposta(MensagemResposta reply) {
        String json = ArquivoJson.objeto(
                ArquivoJson.campoTexto("messageType", "reply"),
                ArquivoJson.campoTexto("requestId", reply.getRequestId()),
                ArquivoJson.campoBooleano("success", reply.isSuccess()),
                ArquivoJson.campoTexto("message", reply.getMessage()),
                ArquivoJson.campoJson("result", reply.getResultJson()));
        return ArquivoJson.paraBytes(json);
    }

    public static MensagemResposta decodificarResposta(byte[] replyBytes) {
        String json = ArquivoJson.deBytes(replyBytes);
        return new MensagemResposta(
                ArquivoJson.valorTexto(json, "requestId"),
                Boolean.parseBoolean(ArquivoJson.valorBruto(json, "success")),
                ArquivoJson.valorTexto(json, "message"),
                ArquivoJson.valorBruto(json, "result"));
    }
}
