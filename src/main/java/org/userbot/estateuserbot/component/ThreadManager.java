package org.userbot.estateuserbot.component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.drinkless.tdlib.TdApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class ThreadManager {

    private final BlockingQueue<TdApi.UpdateNewMessage> queue;
    private final Executor executor;

    private final MessageProcessor processor;

    public ThreadManager(BlockingQueue<TdApi.UpdateNewMessage> queue,
                                     @Qualifier("telegramExecutor") Executor executor,
                         MessageProcessor processor) {
        this.queue = queue;
        this.executor = executor;
        this.processor = processor;
    }

    @PostConstruct
    public void start() {
        Thread dispatcherThread = new Thread(this::loop, "TG-Dispatcher");
        dispatcherThread.setDaemon(true);
        dispatcherThread.start();
        log.info("Started TG-Dispatcher thread");
    }

    private void loop() {
        while (true) {
            try {
                TdApi.UpdateNewMessage msg = queue.take();
                executor.execute(() -> processor.process(msg));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
