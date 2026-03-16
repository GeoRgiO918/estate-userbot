package org.userbot.estateuserbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "prediction")
public class PredictionProperties {

    private String preditionServerUrl;

    public String getPreditionServerUrl() {
        return preditionServerUrl;
    }

    public void setPreditionServerUrl(String preditionServerUrl) {
        this.preditionServerUrl = preditionServerUrl;
    }
}
