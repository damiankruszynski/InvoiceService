package com.invoiceservice.domain.exceptions;

public class NoInvoiceFoundException extends RuntimeException{
    public NoInvoiceFoundException(String number){
        super("Invoice by number "+number+" was not found!");
    }
}
