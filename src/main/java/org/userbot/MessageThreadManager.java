package org.userbot;

import org.drinkless.tdlib.TdApi;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageThreadManager {

    private static Thread messageThread;
    private static BlockingQueue<TdApi.UpdateNewMessage> queue = new LinkedBlockingQueue<>();

    public  static void initThread(){
        messageThread = new MessageThread(queue);
        messageThread.setDaemon(true); // фоновый
        messageThread.setName("org.userbot.MessageThread");
        messageThread.start();
    }

    public static void sendToThread(TdApi.UpdateNewMessage data) {
        queue.offer(data);
    }
}
