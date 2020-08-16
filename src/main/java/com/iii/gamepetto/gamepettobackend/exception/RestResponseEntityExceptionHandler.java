package com.iii.gamepetto.gamepettobackend.exception;

import com.iii.gamepetto.gamepettobackend.validator.ErrorDetails;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public RestResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed");
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errorDetails.addFieldError(e.getField(), e.getRejectedValue(), messageSource.getMessage(e, LocaleContextHolder.getLocale())));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GamepettoEntityNotFoundException.class)
    public ResponseEntity<Object> handleGamepettoEntityNotFound(GamepettoEntityNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Entity couldn't be found");
        errorDetails.addFieldError(ex.getFieldName(), ex.getValue(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
