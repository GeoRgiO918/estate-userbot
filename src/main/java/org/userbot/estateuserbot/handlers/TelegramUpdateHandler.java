package org.userbot.estateuserbot.handlers;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.userbot.estateuserbot.AuthService;
import org.userbot.estateuserbot.Config;
import org.userbot.estateuserbot.ConfigStore;
import org.userbot.estateuserbot.MessageThreadManager;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

public class TelegramUpdateHandler implements Client.ResultHandler {

    private final AuthService authService;
    private final Config config;

    public TelegramUpdateHandler(CompletableFuture<Boolean> auth) {
    authService = new AuthService(auth);
    config = ConfigStore.getConfig();

    }

    @Override
    public void onResult(TdApi.Object object) {
        switch (object.getConstructor()) {
        case TdApi.UpdateAuthorizationState.CONSTRUCTOR:
            authService.onAuthorizationStateUpdated(((TdApi.UpdateAuthorizationState)object).authorizationState);
            break;
        case TdApi.UpdateNewMessage.CONSTRUCTOR:
            TdApi.UpdateNewMessage update = (TdApi.UpdateNewMessage) object;
            if (config.getListenerMode()) {
                MessageThreadManager.sendToThread(update);
            }else{
                System.out.println("Skipped one message analyze" + LocalTime.now());
            }

            break;
        }

        }

    }

