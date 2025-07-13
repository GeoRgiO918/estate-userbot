package org.userbot;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.userbot.handlers.BotUserInfoHandler;
import org.userbot.handlers.TelegramUpdateHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class TelegramClientInit {

    public static void start() throws InterruptedException {
        CompletableFuture<Boolean> auth = new CompletableFuture<>();

        try {
            Client.execute(new TdApi.SetLogVerbosityLevel(0));
        }
        catch(Exception e){
            System.out.println("Failed to set log level to zero");
        }
        TelegramUpdateHandler updateHandler = new TelegramUpdateHandler(auth);
        Client client = Client.create(
                updateHandler::onResult,
                null,
                null
        );
        System.out.println("Authorization started!");
        TelegramClient telegramClient = new TelegramClient(client);
        ClientStore.setClient(telegramClient);
        try {
            auth.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        client.send(new TdApi.GetMe(),new BotUserInfoHandler());
    }
}
