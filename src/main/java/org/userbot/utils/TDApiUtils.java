package org.userbot.utils;

import org.drinkless.tdlib.TdApi;

public class TDApiUtils {

    public static TdApi.InputMessageText toInputMessageText(String text){
        return  new TdApi.InputMessageText(
                new TdApi.FormattedText(text, null), null, false
        );
    }

    public static TdApi.SendMessage toSendMessage(long chatId, TdApi.InputMessageContent content){
        return new TdApi.SendMessage(
                chatId,
                0,
                null,
                null,
                null,
                content
        );
    }
    public static long getUserId(TdApi.UpdateNewMessage update){
        TdApi.MessageSender senderId = update.message.senderId;

        if (!(senderId instanceof TdApi.MessageSenderUser)) {
            throw new IllegalArgumentException("Unsupported sender type");
        }
        return ((TdApi.MessageSenderUser) senderId).userId;
    }
    public static String getOnlyText(TdApi.UpdateNewMessage update){
        TdApi.Message message = update.message;
        if (message.content instanceof TdApi.MessageText) {
            return ((TdApi.MessageText) message.content).text.text;
        }else{
            return "";
        }
    }
}
