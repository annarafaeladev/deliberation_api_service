package br.com.deliberation_api.application.exception;


public class SessionException extends RuntimeException {
    public SessionException(String message) {
        super(message);
    }
}

