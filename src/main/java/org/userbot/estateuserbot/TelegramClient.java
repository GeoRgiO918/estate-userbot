package org.userbot.estateuserbot;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.userbot.estateuserbot.handlers.ResultHandler;

import java.util.concurrent.CompletableFuture;

public class TelegramClient {

    private final Client client;

    public TelegramClient(Client client){
        this.client = client;
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
