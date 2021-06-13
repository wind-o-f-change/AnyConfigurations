package ru.ocrv.uad.backend.config.exeptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ocrv.uad.backend.core.exception.UadApiException;
import ru.ocrv.uad.backend.core.exception.UadRESTServerException;

@RestControllerAdvice
public class ApiRequestExceptionHandler {

    @ExceptionHandler(value = {UadApiException.class})
    public ResponseEntity<Object> requestExceptionHandler(UadApiException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ApiException(exception.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> requestExceptionHandler(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiException(exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> requestExceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiException(exception.getMessage()));
    }

    @ExceptionHandler(value = {JsonMappingException.class})
    public ResponseEntity<Object> requestExceptionHandler(JsonMappingException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiException(exception.getOriginalMessage()));
    }

    @ExceptionHandler(UadRESTServerException.class)
    public ResponseEntity<Object> requestExceptionHandler(UadRESTServerException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiException(exception.getCodeHttp(), exception.getMessage()));
    }
}