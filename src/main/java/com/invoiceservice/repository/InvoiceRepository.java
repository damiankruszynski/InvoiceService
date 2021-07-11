package com.invoiceservice.repository;

import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findOneByNumber(String number);
    List<Invoice> findAllByCompany(Company company, Pageable pageable);


}
