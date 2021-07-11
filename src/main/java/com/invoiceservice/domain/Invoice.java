package com.invoiceservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVOICE")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public final class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String number;
    private boolean paid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany( targetEntity = InvoicePosition.class,
                mappedBy = "invoice",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    private List<InvoicePosition> soldProducts = new ArrayList<>();

    private LocalDate dateOfIssue;

    public Invoice(String number, Company company, List<InvoicePosition> soldProducts, LocalDate dateOfIssue  ){
        this.number = number;
        this.company = company;
        this.soldProducts = soldProducts;
        this.dateOfIssue = dateOfIssue;
    }
}
