package com.invoiceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class InvoicePostionDto {
    private String productName;
    private String category;
    private double price;
    @JsonIgnore
    private String invoiceNumber;

    public InvoicePostionDto(final String productName, final String category,final double price){
        this.productName = productName;
        this.category = category;
        this.price = price;
    }
}
