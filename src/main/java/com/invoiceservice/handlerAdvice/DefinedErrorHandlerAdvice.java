package com.invoiceservice.handlerAdvice;

import com.invoiceservice.domain.exceptions.*;
import com.invoiceservice.payload.response.JsonResponseException;
import com.invoiceservice.domain.exceptions.*;
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
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DefinedErrorHandlerAdvice {

    @ExceptionHandler(NoCompanyFoundException.class)
    public ResponseEntity<JsonResponseException> handleNoCompanyFoundException(NoCompanyFoundException noCompanyFoundException) {
        log.info(noCompanyFoundException.toString());
        return new ResponseEntity<JsonResponseException>(new JsonResponseException(HttpStatus.NOT_FOUND.value(), noCompanyFoundException.getMessage() ),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompanyExistException.class)
    public ResponseEntity<JsonResponseException> handleCompanyExistException(CompanyExistException companyExistException) {
        log.info(companyExistException.toString());
        return new ResponseEntity<JsonResponseException>(new JsonResponseException(HttpStatus.NOT_ACCEPTABLE.value(), companyExistException.getMessage()),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoInvoiceFoundException.class)
    public ResponseEntity<JsonResponseException> handleNoInvoiceFoundException(NoInvoiceFoundException noInvoiceFoundException) {
        log.info(noInvoiceFoundException.toString());
        return new ResponseEntity<JsonResponseException>(new JsonResponseException(HttpStatus.NOT_FOUND.value(), noInvoiceFoundException.getMessage() ),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvoiceExistException.class)
    public ResponseEntity<JsonResponseException> handleInvoiceExistException(InvoiceExistException invoiceExistException) {
        log.info(invoiceExistException.toString());
        return new ResponseEntity<JsonResponseException>(new JsonResponseException(HttpStatus.NOT_ACCEPTABLE.value(), invoiceExistException.getMessage() ),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(WrongParametersException.class)
    public ResponseEntity<JsonResponseException> handleWrongParametersException(WrongParametersException wrongParametersException) {
        log.info(wrongParametersException.toString());
        return new ResponseEntity<JsonResponseException>(new JsonResponseException(HttpStatus.NOT_ACCEPTABLE.value(), wrongParametersException.getMessage() ),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ExternalCompanyRestException.class)
    public ResponseEntity<JsonResponseException> handleExternalCompanyRestException(ExternalCompanyRestException externalCompanyRestException) {
        log.info(externalCompanyRestException.toString());
        return new ResponseEntity<JsonResponseException>(new JsonResponseException(HttpStatus.NOT_ACCEPTABLE.value(), externalCompanyRestException.getMessage() ),
                HttpStatus.SERVICE_UNAVAILABLE);
    }


}
