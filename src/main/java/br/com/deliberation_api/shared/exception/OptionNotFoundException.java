package br.com.deliberation_api.shared.exception;


public class OptionNotFoundException extends RuntimeException {
    public OptionNotFoundException(String message) {
        super(message);
    }
}

