package org.userbot.estateuserbot.utils;

import org.drinkless.tdlib.TdApi;
import org.userbot.estateuserbot.config.TelegramProperties;

public class TDLibParametrsUtil {

    public static  TdApi.SetTdlibParameters get(TelegramProperties properties){

        TdApi.SetTdlibParameters parameters = new TdApi.SetTdlibParameters();
        parameters.apiId = properties.getApiId();
        parameters.apiHash = properties.getApiHash();
        parameters.databaseDirectory = "tdlib-db";
        parameters.deviceModel = "Java client";
        parameters.systemLanguageCode = "en";
        parameters.applicationVersion = "1.0";
        parameters.useMessageDatabase = true;

        return parameters;
    }
}
