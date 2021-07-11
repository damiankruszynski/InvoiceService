package com.invoiceservice.domain.exceptions;

public class WrongParametersException extends RuntimeException{
    public WrongParametersException(String parameterName){
        super("Paramter "+ parameterName +" out of range");
    }
}
