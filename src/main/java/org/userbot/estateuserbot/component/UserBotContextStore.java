package org.userbot.estateuserbot.component;

import lombok.extern.slf4j.Slf4j;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Component;
import org.userbot.estateuserbot.handlers.ResultHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class UserBotContextStore {

    public final TelegramClient client;

    private final CompletableFuture<TdApi.User> user = new CompletableFuture<>();

    public UserBotContextStore(TelegramClient client) {
        this.client = client;
    }

    public void loadContext() throws Exception {

        client.sendAsync(new TdApi.GetMe(), response -> {

            if (response instanceof TdApi.User userResponse) {
                user.complete(userResponse);
                log.info("Successfully received bot context: {}", userResponse);
            } else if (response instanceof TdApi.Error error) {
                log.error("Error while getting bot context: {}", error);
                throw new RuntimeException("Error while getting bot context");
            } else {
                log.warn("Unexpected response from TDLib: {}", response);
            }

        });
    }


    public TdApi.User getContext(){
        try {
            return user.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
