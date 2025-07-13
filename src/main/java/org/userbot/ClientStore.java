package org.userbot;

import org.drinkless.tdlib.Client;

public class ClientStore {

    private static TelegramClient client;

    private ClientStore() {
        // Приватный конструктор, чтобы нельзя было создать экземпляр этого класса
    }

    public static void setClient(TelegramClient clientInstance) {
        if (client != null) {
            throw new IllegalStateException("Client is already set. Cannot override existing TDLib client.");
        }
        client = clientInstance;
    }

    public static TelegramClient getClient() {
        if (client == null) {
            throw new IllegalStateException("TDLib Client has not been initialized yet.");
        }
        return client;
    }


}
