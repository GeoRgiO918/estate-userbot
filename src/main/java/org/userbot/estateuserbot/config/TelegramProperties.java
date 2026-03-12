package org.userbot.estateuserbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;




@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {

    private int apiId;
    private String apiHash;
    private String phoneNumber;
    private boolean listenerMode;
    private String adminUsername;
    private String flaskServerUrl;

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getApiHash() {
        return apiHash;
    }

    public void setApiHash(String apiHash) {
        this.apiHash = apiHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isListenerMode() {
        return listenerMode;
    }

    public void setListenerMode(boolean listenerMode) {
        this.listenerMode = listenerMode;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getFlaskServerUrl() {
        return flaskServerUrl;
    }

    public void setFlaskServerUrl(String flaskServerUrl) {
        this.flaskServerUrl = flaskServerUrl;
    }
}
