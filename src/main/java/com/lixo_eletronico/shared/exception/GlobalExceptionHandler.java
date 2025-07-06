package com.lixo_eletronico.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final ObjectMapper objectMapper;

	@ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Not Found");
        error.put("message", "Rota não encontrada: " + ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
	
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("message", ex.getReason());
        error.put("timestamp", LocalDateTime.now());
        error.put("status", ex.getStatusCode().value());

        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
		Map<String, Object> body = Map.of("timestamp", LocalDateTime.now(), "status", HttpStatus.BAD_REQUEST.value(),
				"error", "Bad Request", "message", "O corpo da requisição está ausente ou mal formatado");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();

	    Object target = ex.getBindingResult().getTarget();
	    Class<?> clazz = target != null ? target.getClass() : null;

	    for (FieldError err : ex.getBindingResult().getFieldErrors()) {
	        String fieldName = err.getField();

	        // Tenta usar o valor do @JsonProperty, se presente
	        String jsonName = fieldName;
	        if (clazz != null) {
	            try {
	                Field field = clazz.getDeclaredField(fieldName);
	                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
	                if (annotation != null) {
	                    jsonName = annotation.value();
	                }
	            } catch (NoSuchFieldException ignored) {
	                // Fallback para o nome original
	            }
	        }

	        errors.put(jsonName, err.getDefaultMessage());
	    }

	    return buildResponse(HttpStatus.BAD_REQUEST, "Erro de validação", errors);
	}


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Violação de restrição: " + ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Parâmetro inválido: " + ex.getName());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneric(Exception ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado: " + ex.getMessage());
	}

	private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
		return buildResponse(status, message, null);
	}

	private ResponseEntity<Object> buildResponse(HttpStatus status, String message, Object errors) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		if (errors != null) {
			body.put("details", errors);
		}
		return ResponseEntity.status(status).body(body);
	}

}
