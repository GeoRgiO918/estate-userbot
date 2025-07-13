package org.userbot.handlers;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

public class LoggingResultHandler implements Client.ResultHandler {

    String message;

    public LoggingResultHandler(String message){
        this.message = message;
    }

    @Override
    public void onResult(TdApi.Object object) {
        switch (object.getConstructor()) {
            case TdApi.Error.CONSTRUCTOR:
                System.out.println("Received error while " + message);
                System.out.println("Response logs:");
                System.out.println("==========================");
                System.out.println(object);
                System.out.println("==========================");
                break;
            case TdApi.Ok.CONSTRUCTOR:
                System.out.println("Received success respond while " + message);
                break;
            default:
                System.out.println("Unusual answer from telegram:");
                System.out.println("==========================");
                System.out.println(object);
                System.out.println("==========================");
        }
    }
}

