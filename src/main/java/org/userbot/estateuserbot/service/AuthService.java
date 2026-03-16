package org.userbot.estateuserbot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.drinkless.tdlib.TdApi;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.userbot.estateuserbot.component.TelegramClient;
import org.userbot.estateuserbot.config.TelegramProperties;
import org.userbot.estateuserbot.entity.AuthenticationStatus;
import org.userbot.estateuserbot.handlers.LoggingResultHandler;
import org.userbot.estateuserbot.utils.TDLibParametrsUtil;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AuthService {

    private final TelegramClient client;
    private final TelegramProperties properties;

    private CompletableFuture<String> telegramCode;
    private CompletableFuture<String> password;

    @Getter
    private volatile AuthenticationStatus status = AuthenticationStatus.NOT_STARTED;

    public AuthService(@Lazy TelegramClient client, TelegramProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    public void onAuthorizationStateUpdated(TdApi.AuthorizationState state) {

        switch (state.getConstructor()) {

            case TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                status = AuthenticationStatus.STARTED;
                handleWaitTdlibParameters(state);
            }

            case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                handleWaitPhoneNumber(state);
            }

            case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                handleWaitCode(state);
            }

            case TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                handleWaitPassword(state);
            }

            case TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                status = AuthenticationStatus.SUCCEED;
                log.info("Authorization completed!");
            }

            default -> {
                status = AuthenticationStatus.FAILED;
                log.warn("Unsupported authorization state: {}", state.getClass().getSimpleName());
            }
        }
    }

    private void handleWaitTdlibParameters(TdApi.AuthorizationState state) {
        log.info("Authorization state received: AuthorizationStateWaitTdlibParameters");
        log.info("Sending tdLib parameters");

        TdApi.SetTdlibParameters params = TDLibParametrsUtil.get(properties);

        client.sendAsync(params, new LoggingResultHandler("sending tdLib params"));
    }

    private void handleWaitPhoneNumber(TdApi.AuthorizationState state) {
        log.info("Authorization state received: AuthorizationStateWaitPhoneNumber");
        log.info("Sending phone number {}", properties.getPhoneNumber());

        client.sendAsync(
                new TdApi.SetAuthenticationPhoneNumber(properties.getPhoneNumber(), null),
                new LoggingResultHandler("sending phone number")
        );
    }

    private void handleWaitCode(TdApi.AuthorizationState state) {
        log.info("Authorization state received: AuthorizationStateWaitCode");

        telegramCode = new CompletableFuture<>();

        log.info("Waiting for  authentication code");
        telegramCode.thenAccept(code -> {
            log.info("Sending authentication code");
            client.sendAsync(
                    new TdApi.CheckAuthenticationCode(code),
                    new LoggingResultHandler("sending code")
            );
        });
    }

    private void handleWaitPassword(TdApi.AuthorizationState state) {
        log.info("Authorization state received: AuthorizationStateWaitPassword");

        password = new CompletableFuture<>();
        log.info("Waiting for password");
        password.thenAccept(pass -> {
            log.info("Sending 2FA password");
            client.sendAsync(
                    new TdApi.CheckAuthenticationPassword(pass),
                    new LoggingResultHandler("sending password")
            );
        });
    }

    public void submitCode(String code) {
        if (telegramCode != null && !telegramCode.isDone()) {
            telegramCode.complete(code);
        }else{
            throw new IllegalStateException("Code already send!");
        }
    }

    public void submitPassword(String pass) {
        if (password != null && !password.isDone()) {
            password.complete(pass);
        }else{
            throw new IllegalStateException("Password already send!");
        }
    }


}