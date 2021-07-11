package com.invoiceservice.controller;

import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.InvoiceDto;
import com.invoiceservice.domain.exceptions.InvoiceExistException;
import com.invoiceservice.domain.exceptions.NoCompanyFoundException;
import com.invoiceservice.domain.exceptions.NoInvoiceFoundException;
import com.invoiceservice.domain.exceptions.WrongParametersException;
import com.invoiceservice.mapper.InvoiceMapper;
import com.invoiceservice.payload.response.JsonResponseMessage;
import com.invoiceservice.service.DbService;
import com.invoiceservice.payload.response.CategorySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.*;


@RestController
@RequestMapping("invoice")
public class InvoicesController {


    private final DbService dbService;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoicesController(DbService dbService, InvoiceMapper invoiceMapper){
        this.dbService = dbService;
        this.invoiceMapper = invoiceMapper;
    }


    @GetMapping()
    public InvoiceDto getInvoiceByNumber(@RequestParam("number") String number) throws NoInvoiceFoundException {
        return invoiceMapper.mapToInvoiceDto(dbService.getInvoiceByNumber(number).orElseThrow(() -> new NoInvoiceFoundException(number)));
    }

    @GetMapping("/forCompany")
    public List<InvoiceDto> getListInvoiceForCompany(@RequestParam("nip") Long nip, @PageableDefault(sort = "id") Pageable pageable) throws NoCompanyFoundException, WrongParametersException {
        if (pageable.getPageSize() > 100){
            throw new WrongParametersException("PageSize");
        }
        Company company = dbService.getCompanyByTaxId(nip).orElseThrow(() -> new NoCompanyFoundException(nip));
        return invoiceMapper.mapToInvoiceDtoList(dbService.getInvoicesForCompany(company, pageable));
    }

    @PostMapping()
    public ResponseEntity<JsonResponseMessage> addInvoice(@RequestBody InvoiceDto invoiceDto) throws NoCompanyFoundException, InvoiceExistException {
        if(! dbService.getInvoiceByNumber(invoiceDto.getNumber()).isPresent()) {
            dbService.addInvoice(invoiceMapper.mapToInvoice(invoiceDto));
            return new ResponseEntity<JsonResponseMessage>(new JsonResponseMessage("Invoice was add."), HttpStatus.CREATED);
        }else {
            throw new InvoiceExistException(invoiceDto.getNumber());
        }
    }

    @PutMapping("/change_status")
    public InvoiceDto changeInvoicePaidStatus(@RequestParam("number") String number, @RequestParam("paid_status") boolean paidStatus) throws NoInvoiceFoundException {
        if(dbService.getInvoiceByNumber(number).isPresent()) {
            return  dbService.setPaidStatusInvoiceByNumber(number, paidStatus)
                        .map(invoiceMapper::mapToInvoiceDto).orElse(InvoiceDto.empty());
        }else {
            throw new NoInvoiceFoundException(number);
        }
    }

    @GetMapping("/category_summary")
    public List<CategorySummary> getInvoiceCategorySummary(@RequestParam("number") String number) throws NoInvoiceFoundException {
        if(dbService.getInvoiceByNumber(number).isPresent()) {
            return dbService.getCategorySummaryForInvoice(number);
        }else {
            throw new NoInvoiceFoundException(number);
        }

    }


}
