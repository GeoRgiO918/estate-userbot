package org.userbot;

import java.io.IOException;

public class ConfigStore {

    private static Config config;


    public static synchronized void initConfig() throws IOException{
        config = new Config();
    }

    public static Config getConfig() throws  IllegalArgumentException{

        if(config == null) throw new IllegalArgumentException("Config is not initializated!!");
        return config;
    }
}
