package org.userbot.estateuserbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class PredictionRestClientConfig {

    public final PredictionProperties properties;

    public PredictionRestClientConfig(PredictionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestClient predictionRestClient(){
        return RestClient.builder()
                .baseUrl(properties.getPreditionServerUrl())
                .build();
    }
}
