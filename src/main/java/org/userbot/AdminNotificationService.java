package org.userbot;

import org.drinkless.tdlib.TdApi;
import org.userbot.dto.TelegramMessage;
import org.userbot.utils.StringUtils;
import org.userbot.utils.TDApiUtils;

public class AdminNotificationService {

    public final TelegramClient client;

    public AdminNotificationService(){
        client = ClientStore.getClient();
    }


    public void notifyAdmin(TelegramMessage message, TdApi.Chat adminChat) {

            sendNotify(message, adminChat);


    }


    public void sendNotify(TelegramMessage message,TdApi.Chat adminChat){
        String msg = StringUtils.formatNotification(message);

        TdApi.InputMessageContent content = TDApiUtils.toInputMessageText(msg);

        TdApi.SendMessage sendMessage = TDApiUtils.toSendMessage(adminChat.id,content);

        try {
            Thread.sleep(1000);
            client.sendSync(sendMessage);
        } catch (Exception e) {
            System.out.println("Error while notifying admin " + e.getLocalizedMessage());
            return;
        }

    }
}
