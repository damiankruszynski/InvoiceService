package com.invoiceservice.mapper;


import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.CompanyDto;
import com.invoiceservice.domain.ExternalCompanyDataDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CompanyMapperSuiteTest {

    @Autowired
    CompanyMapper companyMapper;

    @Test
    public void shouldMapCompanyToCompanyDTO(){
        Company company = new Company(12345L,"FIRMA","12345","KATOWICE","MALINOWA 5", null);
        CompanyDto companyDto = companyMapper.mapToCompanyDto(company);

        Assert.assertEquals(company.getTaxId(),companyDto.getTaxId());
        Assert.assertEquals(company.getName(),companyDto.getName());
        Assert.assertEquals(company.getPostalCode(),companyDto.getPostalCode());
        Assert.assertEquals(company.getCity(),companyDto.getCity());
        Assert.assertEquals(company.getAddress(),companyDto.getAddress());
    }

    @Test
    public void shouldMapCompanyDtoToCompany(){
        CompanyDto companyDto = new CompanyDto(12345L,"FIRMA","12345","KATOWICE","MALINOWA 5");
        Company company = companyMapper.mapToCompany(companyDto);

        Assert.assertEquals(companyDto.getTaxId(),company.getTaxId());
        Assert.assertEquals(companyDto.getName(),company.getName());
        Assert.assertEquals(companyDto.getPostalCode(),company.getPostalCode());
        Assert.assertEquals(companyDto.getCity(),company.getCity());
        Assert.assertEquals(companyDto.getAddress(),company.getAddress());
    }

    @Test
    public void shouldMapExternalCompanyDataDtoToCompany(){
        StringBuilder addressSB = new StringBuilder();
        addressSB.append("MALINOWA ").append("10/").append("11");
        ExternalCompanyDataDto externalCompany = new ExternalCompanyDataDto("FIRMA",12345111L,"1245","KATOWICE", "X", "MALINOWA", "10", "11" );
        Company company = companyMapper.mapToCompany(externalCompany);

        Assert.assertEquals(externalCompany.getTax_id(), company.getTaxId());
        Assert.assertEquals(externalCompany.getName(),company.getName());
        Assert.assertEquals(externalCompany.getPostal_code(),company.getPostalCode());
        Assert.assertEquals(externalCompany.getCity(),company.getCity());
        Assert.assertEquals(addressSB.toString(),company.getAddress());
    }

    @Test
    public void shouldMapCompanyListToCompanyListDto(){
        Company company1 = new Company(112345L,"FIRMA1","123455","KATOWICE","MALINOWA 1", null);
        Company company2 = new Company(122345L,"FIRMA2","123455","KATOWICE","MALINOWA 2", null);
        Company company3 = new Company(132345L,"FIRMA3","123455","KATOWICE","MALINOWA 3", null);
        List<Company> companyList = new ArrayList<>();
        companyList.add(company1);
        companyList.add(company2);
        companyList.add(company3);
        List<CompanyDto> companyDtoList = companyMapper.mapToListCompanyDTO(companyList);

        Assert.assertEquals(companyList.size(), companyDtoList.size());
        Assert.assertEquals(3, companyDtoList.size());
        Assert.assertEquals(companyList.get(0).getAddress(), companyDtoList.stream().filter( cmp -> cmp.getTaxId().equals(112345L)).collect(Collectors.toList()).get(0).getAddress());
        Assert.assertEquals(companyList.get(0).getName(), companyDtoList.stream().filter( cmp -> cmp.getTaxId().equals(112345L)).collect(Collectors.toList()).get(0).getName());
        Assert.assertEquals(companyList.get(2).getName(), companyDtoList.stream().filter( cmp -> cmp.getTaxId().equals(132345L)).collect(Collectors.toList()).get(0).getName());
    }

}
