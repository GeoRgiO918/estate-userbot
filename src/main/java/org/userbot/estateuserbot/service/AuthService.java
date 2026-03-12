package org.userbot.estateuserbot.service;

import org.drinkless.tdlib.TdApi;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.userbot.estateuserbot.component.TelegramClient;
import org.userbot.estateuserbot.config.TelegramProperties;
import org.userbot.estateuserbot.handlers.LoggingResultHandler;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthService {

    private final TelegramClient client;
    private final TelegramProperties properties;

    public AuthService(@Lazy TelegramClient client, TelegramProperties properties){
        this.client = client;
        this.properties = properties;

    }

    public void onAuthorizationStateUpdated(TdApi.AuthorizationState state) {

        switch (state.getConstructor()) {
            case TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR:
                System.out.println("Authorization state received: AuthorizationStateWaitTdlibParameters");
                System.out.println("Sending tdLib parametrs");
                TdApi.SetTdlibParameters params = new TdApi.SetTdlibParameters();
                params.apiId = properties.getApiId();
                params.apiHash = properties.getApiHash();
                params.databaseDirectory = "tdlib-db";
                params.deviceModel = "Java client";
                params.systemLanguageCode = "en";
                params.applicationVersion = "1.0";
                params.useMessageDatabase = true;

                client.sendAsync(params, new LoggingResultHandler("sending tdLib params"));
                break;

            case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR:
                System.out.println("Authorization state received: AuthorizationStateWaitPhoneNumber");
                System.out.println("Sending phone number " + properties.getPhoneNumber());
                client.sendAsync(new TdApi.SetAuthenticationPhoneNumber(properties.getPhoneNumber(), null), new LoggingResultHandler("sending phone number"));
                break;
            case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR:
                System.out.println("Authorization state received: AuthorizationStateWaitCode");
                Scanner scanner = new Scanner(System.in);

                // Считываем строку
                System.out.print("Enter code from telegram app: ");
                String code = scanner.nextLine();
                System.out.print("Enter code " + code);
                client.sendAsync(new TdApi.CheckAuthenticationCode(code),new LoggingResultHandler("sending code"));
                break;
            case TdApi.AuthorizationStateReady.CONSTRUCTOR:
                System.out.println("Authorization completed!");
                break;

            default:
                System.out.println("⚠️ Unsupported authorization state: " + state.getClass().getSimpleName());
                break;
    }
    }
}
