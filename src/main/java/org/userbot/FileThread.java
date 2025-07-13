package org.userbot;

import java.util.concurrent.BlockingQueue;

public class FileThread extends Thread{
   private final BlockingQueue<String> queue;
   private final DataSaver saver = new DataSaver("data/saved_data.csv");

   public FileThread(BlockingQueue<String> queue){
       this.queue = queue;
   }

    @Override
    public void run() {
        try {
            while (true) {
                String text = queue.take();
                saver.tryToAddNewData(text);
            }
        } catch (InterruptedException e) {
            System.out.println("Error while saveing data, thread stopped:" + e.getLocalizedMessage());
            Thread.currentThread().interrupt();
        }
    }
}
