package org.userbot.estateuserbot;

import org.drinkless.tdlib.TdApi;

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


