package org.userbot.handlers;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.util.concurrent.CompletableFuture;

public class ResultHandler implements Client.ResultHandler {

    private CompletableFuture<TdApi.Object> future;
    public ResultHandler(CompletableFuture<TdApi.Object> future){
        this.future = future;
    }
    @Override
    public void onResult(TdApi.Object object) {
        future.complete(object);
    }
 }
