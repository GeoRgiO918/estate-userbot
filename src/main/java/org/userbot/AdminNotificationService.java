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


    public void sendNotify(TelegramMessage message, TdApi.Chat adminChat){

        // Теперь отправляем текстовое уведомление
        String msg = StringUtils.formatNotification(message);

        TdApi.InputMessageContent content = TDApiUtils.toInputMessageText(msg);
        TdApi.SendMessage sendMessage = TDApiUtils.toSendMessage(adminChat.id, content);

        try {
            Thread.sleep(1000);
            client.sendSync(sendMessage);
        } catch (Exception e) {
            System.out.println("Error while sending notify to admin: " + e.getLocalizedMessage());
        }

        TdApi.ForwardMessages forward = new TdApi.ForwardMessages();
        forward.chatId = adminChat.id;                          // Кому
        forward.fromChatId = message.getChat().getId();         // Откуда
        forward.messageIds = new long[]{message.getMessageId()}; // Что
        forward.sendCopy = false;                               // Отобразить как пересланное
        forward.removeCaption = false;                          // Сохраняем подписи
        forward.options = null;                                 // Опции по умолчанию

        try {
            client.sendSync(forward);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error while forwarding message to admin:" + e.getLocalizedMessage());
        }

    }
}
