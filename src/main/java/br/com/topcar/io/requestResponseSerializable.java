package br.com.topcar.io;

import java.io.Serializable;

public class requestResponseSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operation;
    private String payload;
    private String clientId;

    public requestResponseSerializable() {
    }

    public requestResponseSerializable(String operation, String payload, String clientId) {
        this.operation = operation;
        this.payload = payload;
        this.clientId = clientId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "requestResponseSerializable{" +
                "operation='" + operation + '\'' +
                ", payload='" + payload + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
