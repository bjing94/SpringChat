package org.bjing.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class ApiError {
    HttpStatus status;
    String message;
    List<String> errors;

}