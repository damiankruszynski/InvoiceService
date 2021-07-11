package com.invoiceservice.client;

import com.invoiceservice.config.ExternalCompanyConfig;
import com.invoiceservice.domain.exceptions.ExternalCompanyRestException;
import com.invoiceservice.domain.ExternalCompanyDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class CompanyDataProviderClient {

    private final RestTemplate restTemplate;
    private final ExternalCompanyConfig externalCompanyConfig;

    @Autowired
    public CompanyDataProviderClient(RestTemplate restTemplate, ExternalCompanyConfig externalCompanyConfig){
        this.restTemplate = restTemplate;
        this.externalCompanyConfig = externalCompanyConfig;
    }


    public ExternalCompanyDataDto getCompanyData(Long taxId) throws ExternalCompanyRestException {
        try {
            return restTemplate.getForObject(
                    buildUrlToGetInformationAboutCompanyByTaxId(taxId),
                    ExternalCompanyDataDto.class);
        }
        catch (RestClientException restClientException){
            log.error(restClientException.getMessage());
            throw new ExternalCompanyRestException();
        }
    }

    private URI buildUrlToGetInformationAboutCompanyByTaxId(Long taxId){
        return  UriComponentsBuilder.fromHttpUrl(externalCompanyConfig.getExternalUrl())
                .queryParam("token", externalCompanyConfig.getConnectionToken())
                .queryParam("taxId", taxId)
                .build().encode().toUri();
    }
}
