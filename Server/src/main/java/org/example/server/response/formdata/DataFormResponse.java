package org.example.server.response.formdata;

public class DataFormResponse<T> {
    private String message;
    private T data;

    public DataFormResponse() {
    }

    public DataFormResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
