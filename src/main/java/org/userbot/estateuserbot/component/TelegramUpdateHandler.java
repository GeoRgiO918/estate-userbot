package org.userbot.estateuserbot.component;

import lombok.extern.slf4j.Slf4j;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Component;
import org.userbot.estateuserbot.service.AuthService;
import org.userbot.estateuserbot.config.TelegramProperties;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class TelegramUpdateHandler implements Client.ResultHandler {

    private final AuthService authService;
    private final TelegramProperties properties;
    private final BlockingQueue<TdApi.UpdateNewMessage> telegramMessageQueue;
    private AtomicBoolean listenerMode = new AtomicBoolean();
    private AtomicInteger skippedCount = new AtomicInteger(0);

    public TelegramUpdateHandler(AuthService authService, TelegramProperties properties,BlockingQueue<TdApi.UpdateNewMessage> telegramMessageQueue) {
        this.authService = authService;
        this.properties =properties;
        this.telegramMessageQueue =telegramMessageQueue;
        listenerMode.set(properties.isListenerMode());
    }

    @Override
    public void onResult(TdApi.Object object) {
        switch (object.getConstructor()) {
        case TdApi.UpdateAuthorizationState.CONSTRUCTOR ->{
            authService.onAuthorizationStateUpdated(((TdApi.UpdateAuthorizationState)object).authorizationState);
        }
        case TdApi.UpdateNewMessage.CONSTRUCTOR ->{
            handleUpdateNewMessage(object);
        }
        }

    }

    private void handleUpdateNewMessage(TdApi.Object object){
        if (object instanceof TdApi.UpdateNewMessage update) {
            boolean offered = telegramMessageQueue.offer(update);
            if (!offered) {
                log.warn("Telegram message queue is full, dropped message {}", update.message.id);
            }
        }else{
            skippedCount.incrementAndGet();
        }

    }

    public void changeListenerMode(boolean value){
        boolean oldValue = listenerMode.get();
        if(oldValue == value) return;
        skippedCount.set(0);
        listenerMode.set(value);

    }

    public Integer getSkippedCount(){
        return skippedCount.get();
    }
}

