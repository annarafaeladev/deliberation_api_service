package br.com.deliberation_api.infrastructure.exception;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoSocketOpenException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Order(1)
public class MongoExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler({com.mongodb.DuplicateKeyException.class, org.springframework.dao.DuplicateKeyException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, Object>> handleDuplicateKey(Exception ex) {
        log.error("[DuplicateKeyException or DataIntegrityViolationException] A record with the same key already exists.", ex);
        return buildResponse(HttpStatus.CONFLICT, "Duplicate Key Error",
                "A record with the same key already exists.");
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<Map<String, Object>> handleWriteException(MongoWriteException ex) {
        log.error("[MongoWriteException] Error writing to the database.", ex);
        return buildResponse(HttpStatus.BAD_REQUEST, "Write Error",
                "An error occurred.");
    }

    @ExceptionHandler(MongoCommandException.class)
    public ResponseEntity<Map<String, Object>> handleCommandException(MongoCommandException ex) {
        log.error("[MongoCommandException] Database command failed.", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Command Error",
                "Timeout Error. Please try again later.");
    }

    @ExceptionHandler(MongoTimeoutException.class)
    public ResponseEntity<Map<String, Object>> handleTimeout(MongoTimeoutException ex) {
        log.error("[MongoTimeoutException] Database connection timed out.", ex);
        return buildResponse(HttpStatus.GATEWAY_TIMEOUT, "Timeout Error",
                "Timeout Error. Please try again later.");
    }

    @ExceptionHandler(MongoSocketOpenException.class)
    public ResponseEntity<Map<String, Object>> handleSocketOpen(MongoSocketOpenException ex) {
        log.error("[MongoSocketOpenException] Failed to connect to the database.", ex);
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, "Connection Error",
                "Failed connection. Please try again later.");
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<Map<String, Object>> handleGeneralMongoException(MongoException ex) {
        log.error("[MongoException] An unexpected database error occurred.", ex);
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, "Database Error",
                "A database error occurred. Please try again later.");
    }
}
