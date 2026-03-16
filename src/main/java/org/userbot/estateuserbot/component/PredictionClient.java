package org.userbot.estateuserbot.component;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.userbot.estateuserbot.dto.PredictionResponse;

import java.util.Map;

@Component
public class PredictionClient {

    private final RestClient predictionRestClient;

    public PredictionClient(RestClient predictionRestClient) {
        this.predictionRestClient = predictionRestClient;
    }



    public PredictionResponse predict(String message){
        return predictionRestClient.post()
                .uri("/predict")
                .body(Map.of("text", message))
                .retrieve()
                .body(PredictionResponse.class);
    }
}
