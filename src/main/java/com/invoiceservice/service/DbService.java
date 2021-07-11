package com.invoiceservice.service;


import com.invoiceservice.repository.CompanyRepository;
import com.invoiceservice.repository.InvoicePositionRepository;
import com.invoiceservice.repository.InvoiceRepository;
import com.invoiceservice.handlerAdvice.DefinedErrorHandlerAdvice;
import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.Invoice;
import com.invoiceservice.payload.response.CategorySummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public final class DbService {

    private final InvoiceRepository invoiceRepository;
    private final CompanyRepository companyRepository;
    private final InvoicePositionRepository invoicePositionRepository;

    @Autowired
    public DbService(InvoiceRepository invoiceRepository,  CompanyRepository companyRepository, InvoicePositionRepository invoicePositionRepository){
        this.invoiceRepository = invoiceRepository;
        this.companyRepository = companyRepository;
        this.invoicePositionRepository = invoicePositionRepository;
    }

    private static final Logger logger = LogManager.getLogger(DefinedErrorHandlerAdvice.class);

    public Optional<Invoice> getInvoiceByNumber(String invoiceNumber){
       return invoiceRepository.findOneByNumber(invoiceNumber);
    }
    public void addInvoice(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoicesForCompany(Company company, Pageable pageable){
        return invoiceRepository.findAllByCompany(company, pageable);
    }

    public Optional<Company> getCompanyByTaxId(Long taxId){
        return companyRepository.findById(taxId);
    }

    public void addCompany(Company company){
        companyRepository.save(company);
    }

    public Company updateCompany(Company company){
        return companyRepository.save(company);
    }

    public void deleteCompany(Long taxId){
        companyRepository.deleteById(taxId);
    }

    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    public Optional<Invoice> setPaidStatusInvoiceByNumber(String number, boolean paidStatus){
        return  getInvoiceByNumber(number).map( invoice -> {
            invoice.setPaid(paidStatus);
            return invoiceRepository.save(invoice); });

    }

    public List<CategorySummary> getCategorySummaryForInvoice(String invoiceNumber){
        return invoicePositionRepository.getCategorySummaryForInvoice(invoiceNumber);
    }


}
