package com.invoiceservice.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@Setter
public class ExternalCompanyConfig {

    @Value("${external.url}")
    private String externalUrl;
    @Value("${external.token}")
    private String connectionToken;

}
