package org.userbot.estateuserbot.component;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Component;
import org.userbot.estateuserbot.handlers.ResultHandler;

import java.util.concurrent.CompletableFuture;

@Component
public class TelegramClient {

    static{
        // 1. Загружаем tdjni.dll
        System.loadLibrary("libcrypto-3-x64");
        System.loadLibrary("libssl-3-x64");
        System.loadLibrary("zlib1");
        System.loadLibrary("tdjni");
    }

    private final Client client;

    public TelegramClient(TelegramUpdateHandler handler){
        try {
            Client.execute(new TdApi.SetLogVerbosityLevel(0));
        }
        catch(Exception e){
            System.out.println("Failed to set log level to zero");
        }
        client = Client.create(
                handler::onResult,
                null,
                null
        );
    }


    public TdApi.Object sendSync(TdApi.Function function) throws Exception{
        CompletableFuture<TdApi.Object> future = new CompletableFuture<>();
        client.send(function,new ResultHandler(future));
        return future.get();
    }

    public void sendAsync(TdApi.Function function, Client.ResultHandler resultHandler){
        client.send(function,resultHandler);
    }
}
