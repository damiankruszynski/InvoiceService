package com.invoiceservice.domain.exceptions;

public class NoCompanyFoundException extends RuntimeException {
    public NoCompanyFoundException(Long id){
        super("Company of id="+id.toString()+" is not found!");
    }
}
