package com.invoiceservice.domain.exceptions;

public class ExternalCompanyRestException extends RuntimeException{
    public ExternalCompanyRestException(){
        super("Connection to external service has failed.");
    }

}
