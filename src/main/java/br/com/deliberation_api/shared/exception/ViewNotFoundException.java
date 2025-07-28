package br.com.deliberation_api.shared.exception;


public class ViewNotFoundException extends RuntimeException {
    public ViewNotFoundException(String message) {
        super(message);
    }
}

