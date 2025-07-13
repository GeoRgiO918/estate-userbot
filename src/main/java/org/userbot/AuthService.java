package org.userbot;

import org.drinkless.tdlib.TdApi;
import org.drinkless.tdlib.Client;
import org.userbot.handlers.LoggingResultHandler;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class AuthService {

    private final CompletableFuture<Boolean> auth;

    public AuthService(CompletableFuture<Boolean> auth )
    {
        this.auth = auth;
    }

    public void onAuthorizationStateUpdated(TdApi.AuthorizationState state) {
        Config config = ConfigStore.getConfig();
        TelegramClient client = ClientStore.getClient();
        switch (state.getConstructor()) {
            case TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR:
                System.out.println("Authorization state received: AuthorizationStateWaitTdlibParameters");
                System.out.println("Sending tdLib parametrs");
                TdApi.SetTdlibParameters params = new TdApi.SetTdlibParameters();
                params.apiId = config.getApiId();
                params.apiHash = config.getApiHash();
                params.databaseDirectory = "tdlib-db";
                params.deviceModel = "Java client";
                params.systemLanguageCode = "en";
                params.applicationVersion = "1.0";
                params.useMessageDatabase = true;

                client.sendAsync(params, new LoggingResultHandler("sending tdLib params"));
                break;

            case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR:
                System.out.println("Authorization state received: AuthorizationStateWaitPhoneNumber");
                System.out.println("Sending phone number " + config.getPhoneNumber());
                client.sendAsync(new TdApi.SetAuthenticationPhoneNumber(config.getPhoneNumber(), null), new LoggingResultHandler("sending phone number"));
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
                auth.complete(true);
                break;

            default:
                System.out.println("⚠️ Unsupported authorization state: " + state.getClass().getSimpleName());
                break;
    }
    }
}
