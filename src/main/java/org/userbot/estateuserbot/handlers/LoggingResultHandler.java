package org.userbot.estateuserbot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

@Slf4j
public class LoggingResultHandler implements Client.ResultHandler {

    String message;

    public LoggingResultHandler(String message){
        this.message = message;
    }

    @Override
    public void onResult(TdApi.Object object) {
        switch (object.getConstructor()) {
            case TdApi.Error.CONSTRUCTOR:
                log.error("Received error response while {}. Error: {}",message,object.toString());
                break;
            case TdApi.Ok.CONSTRUCTOR:
                log.info("Received success response while {}",message);
                break;
            default:
                log.warn("Unusual response from Telegram while {}",message);
        }
    }
}

