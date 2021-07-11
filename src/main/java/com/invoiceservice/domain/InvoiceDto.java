package com.invoiceservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class InvoiceDto {
    @NotNull
    private String number;
    private boolean paid = false;
    @NotNull
    @JsonProperty("nip")
    private Long taxId;
    @JsonProperty("products")
    private List<InvoicePostionDto> soldProducts;
    private LocalDate dateOfIssue = LocalDate.now();

    public static InvoiceDto empty(){
        return new InvoiceDto();
    }
}
