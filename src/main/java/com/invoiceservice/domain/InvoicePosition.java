package com.invoiceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="INVOICE_POSTION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class InvoicePosition {

    private static int CURRENT_ID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    private String productName;

    @Column(name = "CATEGORY")
    private String category;

    private double price;

    public InvoicePosition(final String productName, final String category, final double price){
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

}
