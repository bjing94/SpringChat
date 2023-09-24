package org.bjing.chat.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        ApiError apiError = ApiError.builder()
                .errors(fieldErrors.stream().map(field ->
                        field.getField() + ": " + field.getDefaultMessage()).toList())
                .message("Validation failed")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        System.out.println("Handle exception");
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Object> handleJwtException(ExpiredJwtException exception) {
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }
}
