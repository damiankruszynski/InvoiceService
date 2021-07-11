package com.invoiceservice.handlerAdvice;

import com.invoiceservice.payload.response.JsonResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class OtherErrorsHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponseException> handleOtherExceptions(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<JsonResponseException>(new JsonResponseException( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage() ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
