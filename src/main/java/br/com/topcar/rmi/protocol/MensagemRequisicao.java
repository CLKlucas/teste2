package br.com.topcar.rmi.protocol;

public class MensagemRequisicao {
    private final String requestId;
    private final String objectReference;
    private final String methodId;
    private final String argumentsJson;

    public MensagemRequisicao(String requestId, String objectReference, String methodId, String argumentsJson) {
        this.requestId = requestId;
        this.objectReference = objectReference;
        this.methodId = methodId;

        if (argumentsJson == null || argumentsJson.isBlank()) {
            this.argumentsJson = "{}";
        } else {
            this.argumentsJson = argumentsJson;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getArgumentsJson() {
        return argumentsJson;
    }
}
