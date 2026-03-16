package org.userbot.estateuserbot.config;

import org.drinkless.tdlib.TdApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TelegramMessageQueueConfig {

    @Bean
    public BlockingQueue<TdApi.UpdateNewMessage> telegramMessageQueue() {
        return new LinkedBlockingQueue<>(10000);
    }
}
