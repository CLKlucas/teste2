package br.com.topcar.io;

import java.io.Serializable;
import java.time.LocalDate;

public class replyResponseSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
    private LocalDate data;

    public replyResponseSerializable() {
    }

    public replyResponseSerializable(boolean success, String message, LocalDate data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "replyResponseSerializable{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
