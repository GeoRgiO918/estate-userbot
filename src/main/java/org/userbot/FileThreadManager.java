package org.userbot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileThreadManager {
    private static Thread fileThread;
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public  static void initThread(){
        fileThread = new FileThread(queue);
        fileThread.setDaemon(true); // фоновый
        fileThread.setName("org.userbot.FileThread");
        fileThread.start();

    }

    public static void sendToThread(String data) {
        queue.offer(data);
    }


}
