package org.userbot.estateuserbot.component;

import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class UserBotContextStore {

    public final TelegramClient client;

    private final CompletableFuture<TdApi.User> user = new CompletableFuture<>();

    public UserBotContextStore(TelegramClient client) {
        this.client = client;
    }

    public void loadContext(){

    }

    public TdApi.User getContext(){
        try {
            user.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
