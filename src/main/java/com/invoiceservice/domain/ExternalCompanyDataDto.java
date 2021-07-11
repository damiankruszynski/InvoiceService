package com.invoiceservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExternalCompanyDataDto {
    private final String name;
    private final Long tax_id;
    private final String postal_code;
    private final String city;
    private final String state;
    private final String street;
    private final String house_number;
    private final String appartment;
}
