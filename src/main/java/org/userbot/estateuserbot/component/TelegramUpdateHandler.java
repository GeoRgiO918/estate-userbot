package org.userbot.estateuserbot.component;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Component;
import org.userbot.estateuserbot.service.AuthService;
import org.userbot.estateuserbot.MessageThreadManager;
import org.userbot.estateuserbot.config.TelegramProperties;

import java.time.LocalTime;

@Component
public class TelegramUpdateHandler implements Client.ResultHandler {

    private final AuthService authService;
    private final TelegramProperties properties;

    public TelegramUpdateHandler(AuthService authService, TelegramProperties properties) {
        this.authService = authService;
        this.properties =properties;
    }

    @Override
    public void onResult(TdApi.Object object) {
        switch (object.getConstructor()) {
        case TdApi.UpdateAuthorizationState.CONSTRUCTOR:
            authService.onAuthorizationStateUpdated(((TdApi.UpdateAuthorizationState)object).authorizationState);
            break;
        case TdApi.UpdateNewMessage.CONSTRUCTOR:
            TdApi.UpdateNewMessage update = (TdApi.UpdateNewMessage) object;
            if (properties.isListenerMode()) {
                MessageThreadManager.sendToThread(update);
            }else{
                System.out.println("Skipped one message analyze" + LocalTime.now());
            }

            break;
        }

        }

    }

