package org.userbot;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

public class UserBotContextStore {

    private static TdApi.User user;


    public static void setContext(TdApi.User userInstance) {
        if (user != null) {
            throw new IllegalStateException("Client is already set. Cannot override existing TDLib client.");
        }
        user = userInstance;
    }

    public static TdApi.User getContext() {
        if (user == null) {
            throw new IllegalStateException("User bot context has not been initialized yet.");
        }
        return user;
    }

}
