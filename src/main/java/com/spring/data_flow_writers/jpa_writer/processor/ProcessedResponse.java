package com.spring.data_flow_writers.jpa_writer.processor;

public class ProcessedResponse<T> {
    private T response;

    public ProcessedResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }
}
