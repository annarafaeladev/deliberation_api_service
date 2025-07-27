package br.com.deliberation_api.shared.exception;


public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }
}

