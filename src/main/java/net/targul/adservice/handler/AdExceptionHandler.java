package net.targul.adservice.handler;

import net.targul.adservice.exception.ad.AdStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdExceptionHandler {

    @ExceptionHandler(AdStatusException.class)
    public ResponseEntity<String> handleAdStatusException(AdStatusException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}