package com.invoiceservice.mapper;


import com.invoiceservice.domain.CompanyDto;
import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.ExternalCompanyDataDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public CompanyDto mapToCompanyDto(Company company){
        return new CompanyDto(company.getTaxId(), company.getName(),company.getPostalCode(), company.getCity(), company.getAddress());
    }

    public Company mapToCompany(CompanyDto companyDto){
        return new Company(companyDto.getTaxId(), companyDto.getName(),companyDto.getPostalCode(), companyDto.getCity(), companyDto.getAddress(), null);
    }

    public Company mapToCompany(ExternalCompanyDataDto externalCompanyDataDto){
        StringBuilder addressSB = new StringBuilder();
        addressSB.append(externalCompanyDataDto.getStreet()+" ").append(externalCompanyDataDto.getHouse_number()+"/").append(externalCompanyDataDto.getAppartment());
        return new Company(externalCompanyDataDto.getTax_id(),externalCompanyDataDto.getName(),externalCompanyDataDto.getPostal_code(),
                externalCompanyDataDto.getCity(),
                addressSB.toString(), null);
    }

    public List<CompanyDto> mapToListCompanyDTO(List<Company> companyList){
        return  companyList.stream()
                .map( company -> mapToCompanyDto(company) )
                .collect(Collectors.toList());
    }

}
