package com.invoiceservice.controller;


import com.invoiceservice.client.CompanyDataProviderClient;
import com.invoiceservice.domain.CompanyDto;
import com.invoiceservice.domain.exceptions.CompanyExistException;
import com.invoiceservice.domain.exceptions.ExternalCompanyRestException;
import com.invoiceservice.domain.exceptions.NoCompanyFoundException;
import com.invoiceservice.mapper.CompanyMapper;
import com.invoiceservice.payload.response.JsonResponseMessage;
import com.invoiceservice.service.DbService;
import com.invoiceservice.domain.ExternalCompanyDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company")
public class CompanyController {

    private final CompanyMapper companyMapper;
    private final DbService dbService;
    private final CompanyDataProviderClient companyDataProviderClient;

    @Autowired
    public CompanyController(CompanyMapper companyMapper,DbService dbService, CompanyDataProviderClient companyDataProviderClient ){
        this.companyMapper = companyMapper;
        this.dbService = dbService;
        this.companyDataProviderClient = companyDataProviderClient;
    }


    @GetMapping("/web/{nip}")
    public ExternalCompanyDataDto getExternalCompany(@PathVariable Long nip) throws ExternalCompanyRestException {
        return companyDataProviderClient.getCompanyData(nip);
    }

    @PostMapping("/web/{nip}")
    public ResponseEntity<JsonResponseMessage> addExternalCompany(@PathVariable Long nip) throws ExternalCompanyRestException, CompanyExistException {
        if(dbService.getCompanyByTaxId(nip).isPresent()){
            throw new CompanyExistException(nip);
        }
        companyMapper.mapToCompany(companyDataProviderClient.getCompanyData(nip));
        return new ResponseEntity<JsonResponseMessage>(new JsonResponseMessage("Company from external service was add."),HttpStatus.OK);
    }

    @GetMapping("/{nip}")
    public CompanyDto getCompany(@PathVariable Long nip) throws NoCompanyFoundException {
       return companyMapper.mapToCompanyDto(dbService.getCompanyByTaxId(nip).orElseThrow(() -> new NoCompanyFoundException(nip)));
    }

    @GetMapping("/all")
    public List<CompanyDto> getAllCompany(){
        return companyMapper.mapToListCompanyDTO(dbService.getAllCompany());
    }

    @PostMapping()
    public ResponseEntity<JsonResponseMessage> addCompany(@RequestBody CompanyDto companyDto) throws CompanyExistException {
        if(! dbService.getCompanyByTaxId(companyDto.getTaxId()).isPresent()) {
            dbService.addCompany(companyMapper.mapToCompany(companyDto));
            return new ResponseEntity<JsonResponseMessage>(new JsonResponseMessage("Company was add."), HttpStatus.CREATED);
        }else {
            throw new CompanyExistException(companyDto.getTaxId());
        }
    }

    @PutMapping()
    public CompanyDto updateCompany(@RequestBody CompanyDto companyDto) throws NoCompanyFoundException{
        if(dbService.getCompanyByTaxId((companyDto.getTaxId())).isPresent()){
           return  companyMapper.mapToCompanyDto(dbService.updateCompany(companyMapper.mapToCompany(companyDto)));
        }else{
            throw new NoCompanyFoundException(companyDto.getTaxId());
        }
    }

    @DeleteMapping("/{nip}")
    public ResponseEntity<JsonResponseMessage> deleteCompany(@PathVariable Long nip) throws NoCompanyFoundException{
        if(dbService.getCompanyByTaxId(nip).isPresent()) {
            dbService.deleteCompany(nip);
            return new ResponseEntity<JsonResponseMessage>(new JsonResponseMessage("Company was deleted."),HttpStatus.OK);
        } else {
            throw new NoCompanyFoundException(nip);
        }

    }




}
