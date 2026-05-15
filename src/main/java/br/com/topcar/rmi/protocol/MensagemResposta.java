package br.com.topcar.rmi.protocol;

public class MensagemResposta {
    private final String requestId;
    private final boolean success;
    private final String message;
    private final String resultJson;

    public MensagemResposta(String requestId, boolean success, String message, String resultJson) {
        this.requestId = requestId;
        this.success = success;
        this.message = message;

        if (resultJson == null || resultJson.isBlank()) {
            this.resultJson = "{}";
        } else {
            this.resultJson = resultJson;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getResultJson() {
        return resultJson;
    }
}
