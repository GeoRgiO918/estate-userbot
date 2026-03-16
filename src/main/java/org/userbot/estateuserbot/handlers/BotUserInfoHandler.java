package org.userbot.estateuserbot.handlers;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.userbot.estateuserbot.component.UserBotContextStore;

public class BotUserInfoHandler implements Client.ResultHandler {

    @Override
    public void onResult(TdApi.Object object) {
        UserBotContextStore.setContext((TdApi.User)object);
    }
}
