package br.com.deliberation_api.infrastructure.exception;

import br.com.deliberation_api.shared.exception.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String error,
            Object messageOrErrors
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);

        if (messageOrErrors instanceof String) {
            body.put("message", messageOrErrors);
        } else if (messageOrErrors instanceof List) {
            body.put("errors", messageOrErrors);
        } else {
            body.put("message", "Unexpected error");
        }

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTopicNotFoundException(TopicNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Topic Not Found", ex.getMessage());
    }

    @ExceptionHandler(ViewNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleViewNotFoundException(ViewNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "View Not Found", ex.getMessage());
    }

    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOptionNotFoundException(OptionNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Option Not Found", ex.getMessage());
    }

    @ExceptionHandler(SessionException.class)
    public ResponseEntity<Map<String, Object>> handleSessionException(SessionException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Session Error", ex.getMessage());
    }

    @ExceptionHandler(VoteException.class)
    public ResponseEntity<Map<String, Object>> handleVoteException(VoteException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Vote Error", ex.getMessage());
    }

    @ExceptionHandler(VoteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleVoteNotFoundException(VoteNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Vote Error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred";
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", message);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Method HTTP", ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Null reference error occurred", "Null value error detected");
    }

    @ExceptionHandler(NoSuchMethodError.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchMethodError(NoSuchMethodError ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Method Error", "We were unable to load the API documentation. Please try again later or contact support.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Request Error", "Invalid request body format. Please check the JSON structure and try again.");
    }

    @ExceptionHandler(AssociateException.class)
    public ResponseEntity<Map<String, Object>> handleAssociateException(AssociateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Associate error", ex.getMessage());
    }

    @ExceptionHandler(AssociateNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAssociateNotFoundException(AssociateNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Associate error", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("message", fieldError.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList());

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    }
}
