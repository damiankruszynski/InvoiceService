package com.invoiceservice.domain.exceptions;

public class InvoiceExistException extends  RuntimeException{
    public InvoiceExistException(String number){
        super("Invoice with number "+number+" exists. Can't add.");
    }
}
