package com.invoiceservice.repository;

import com.invoiceservice.domain.InvoicePosition;
import com.invoiceservice.payload.response.CategorySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoicePositionRepository extends JpaRepository<InvoicePosition, Long> {

    @Query(nativeQuery = true, value =
            "SELECT IP.CATEGORY AS category, COUNT(*) AS count " +
              "FROM INVOICE_POSTION IP, INVOICE I " +
            " WHERE I.ID = IP.INVOICE_ID " +
              " AND I.NUMBER = ?1  GROUP BY IP.CATEGORY ")
    List<CategorySummary> getCategorySummaryForInvoice(String invoiceNumber);
}
