package com.invoiceservice.domain.exceptions;

public class CompanyExistException extends RuntimeException {
    public CompanyExistException(Long taxId){
        super("Company of id="+taxId.toString()+" is exist, can't add!");
    }
}
