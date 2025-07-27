package br.com.deliberation_api.shared.exception;


public class VoteNotFoundException extends RuntimeException {
    public VoteNotFoundException(String message) {
        super(message);
    }
}

