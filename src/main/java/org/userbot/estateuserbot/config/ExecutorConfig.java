package org.userbot.estateuserbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    @Value("${telegram.executor.core-pool-size}")
    private int corePoolSize;

    @Value("${telegram.executor.max-pool-size}")
    private int maxPoolSize;

    @Value("${telegram.executor.queue-capacity}")
    private int queueCapacity;

    @Value("${telegram.executor.thread-name-prefix}")
    private String threadNamePrefix;

    @Bean("telegramExecutor")
    public Executor telegramExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}