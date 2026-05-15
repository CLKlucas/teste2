package br.com.topcar.rmi.protocol;

import br.com.topcar.rmi.codec.ArquivoJson;
import br.com.topcar.rmi.codec.ConversorMensagemJson;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Requisitante {
    private static final Map<Integer, String> METHOD_TABLE = new ConcurrentHashMap<>();

    static {
        METHOD_TABLE.put(1, "LISTAR_PECAS");
        METHOD_TABLE.put(2, "BUSCAR_PECA");
        METHOD_TABLE.put(3, "CADASTRAR_CLIENTE");
        METHOD_TABLE.put(4, "CRIAR_PEDIDO");
        METHOD_TABLE.put(5, "CALCULAR_TOTAL_PEDIDO");
        METHOD_TABLE.put(6, "CONSULTAR_CLIENTE");
    }

    private final RequisicaoRespostaRemota remote;
    private final RetornoCliente callback;
    private final AtomicLong sequence = new AtomicLong(1);

    public Requisitante(RequisicaoRespostaRemota remote, RetornoCliente callback) {
        this.remote = remote;
        this.callback = callback;
    }

    public byte[] doOperation(ReferenciaObjetoRemoto objectRef, int methodId, byte[] arguments) throws RemoteException {
        String methodName = METHOD_TABLE.get(methodId);
        if (methodName == null) {
            throw new IllegalArgumentException("Metodo remoto nao mapeado: " + methodId);
        }
        return doOperation(objectRef, methodName, arguments);
    }

    public byte[] doOperation(ReferenciaObjetoRemoto objectRef, String methodId, byte[] arguments) throws RemoteException {
        MensagemRequisicao request = new MensagemRequisicao(
                nextRequestId(),
                objectRef.getObjectName(),
                methodId,
                ArquivoJson.deBytes(arguments));
        return remote.getRequest(ConversorMensagemJson.codificarRequisicao(request), callback);
    }

    public MensagemResposta chamar(ReferenciaObjetoRemoto objectRef, String methodId, String argumentsJson) throws RemoteException {
        byte[] reply = doOperation(objectRef, methodId, ArquivoJson.paraBytes(argumentsJson));
        return ConversorMensagemJson.decodificarResposta(reply);
    }

    private String nextRequestId() {
        return "req-" + System.currentTimeMillis() + "-" + sequence.getAndIncrement();
    }
}
