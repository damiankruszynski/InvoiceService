package com.invoiceservice.mapper;

import com.invoiceservice.domain.InvoiceDto;
import com.invoiceservice.domain.exceptions.NoCompanyFoundException;
import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.Invoice;
import com.invoiceservice.service.DbService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    private final DbService dbService;
    private final InvoicePositionMapper invoicePositionMapper;

    public  InvoiceMapper(DbService dbService, InvoicePositionMapper invoicePositionMapper){
        this.dbService = dbService;
        this.invoicePositionMapper = invoicePositionMapper;
    }

    public InvoiceDto mapToInvoiceDto(Invoice invoice){
        return new InvoiceDto(invoice.getNumber(), invoice.isPaid(), invoice.getCompany().getTaxId(),
                              invoicePositionMapper.mapToInvoicePositionListDto(invoice.getSoldProducts()),invoice.getDateOfIssue());
    }

    public Invoice mapToInvoice(InvoiceDto invoiceDto) throws NoCompanyFoundException {
        Company company = dbService.getCompanyByTaxId(invoiceDto.getTaxId()).orElseThrow(() -> new NoCompanyFoundException(invoiceDto.getTaxId()));
        Invoice invoice = new Invoice(invoiceDto.getNumber(), company, invoicePositionMapper.mapToInvoicePositionList(invoiceDto.getSoldProducts()), invoiceDto.getDateOfIssue());
        invoice.getSoldProducts().stream()
                .forEach(soldProduct -> soldProduct.setInvoice(invoice));
        return invoice;
    }

    public List<InvoiceDto> mapToInvoiceDtoList(List<Invoice> invoiceList){
        return invoiceList.stream()
                .map(invoice -> mapToInvoiceDto(invoice))
                .collect(Collectors.toList());
    }

}
