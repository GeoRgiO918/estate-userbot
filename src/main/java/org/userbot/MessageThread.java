package org.userbot;

import org.drinkless.tdlib.TdApi;
import org.userbot.dto.ChatInfo;
import org.userbot.dto.TelegramMessage;
import org.userbot.dto.UserInfo;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;

public class MessageThread extends Thread{

    private final BlockingQueue<TdApi.UpdateNewMessage> queue;
    private final MessageProcessor processor;

    public MessageThread(BlockingQueue<TdApi.UpdateNewMessage> queue){
        this.queue = queue;
        processor = new MessageProcessor();
    }

    @Override
    public void run() {
        try {
            while (true) {
               processor.process(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


