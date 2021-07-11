package com.invoiceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class CompanyDto {
    @NotNull
    private Long taxId;
    @NotNull
    private String name;
    @NotNull
    private String postalCode;
    @NotNull
    private String city;
    @NotNull
    private String address;
}
