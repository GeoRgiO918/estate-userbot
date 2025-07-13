package org.userbot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final String apiId;
    private final String apiHash;
    private final String phoneNumber;
    private final boolean listenerMode;
    private final String adminUsername;
    private final String flaskServerUrl;

    public Config() throws IOException {
        Properties props = new Properties();
        try (var in = new FileInputStream("config.properties")) {
            props.load(in);
        }

        this.apiId = props.getProperty("api_id");
        this.apiHash = props.getProperty("api_hash");
        this.phoneNumber = props.getProperty("phone_number");
        this.listenerMode = Boolean.parseBoolean(props.getProperty("listener_mode"));
        this.adminUsername = props.getProperty("admin_username");
        this.flaskServerUrl = props.getProperty("flask_server_url");
    }

    public int getApiId() {
        return Integer.parseInt(apiId);
    }

    public String getApiHash() {
        return apiHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public boolean getListenerMode(){
        return listenerMode;
    }
    public String getAdminUsername(){
        return  adminUsername;
    }
    public String getFlaskServerUrl() {
        return flaskServerUrl;
    }
}
