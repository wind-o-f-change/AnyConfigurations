package ru.ocrv.uad.backend.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("swaggerConfigProperties")
public class SwaggerConfigProperties {
    @Value("${api.url}")
    private String apiURL;

    public String getApiURL() {
        return apiURL;
    }
}

