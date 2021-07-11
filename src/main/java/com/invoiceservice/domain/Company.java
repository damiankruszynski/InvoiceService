package com.invoiceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMPANY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class Company {
    @Id
    private Long taxId;
    private String name;
    private String postalCode;
    private String city;
    private String address;

    @JsonIgnore
    @OneToMany(targetEntity = Invoice.class,
               mappedBy = "company",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Invoice> invoices;

}
