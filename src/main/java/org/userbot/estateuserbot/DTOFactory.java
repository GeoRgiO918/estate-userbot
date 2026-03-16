package org.userbot.estateuserbot;

import org.drinkless.tdlib.TdApi;
import org.userbot.estateuserbot.component.UserBotContextStore;
import org.userbot.estateuserbot.dto.ChatInfo;
import org.userbot.estateuserbot.dto.TelegramMessage;
import org.userbot.estateuserbot.dto.UserInfo;
import org.userbot.estateuserbot.utils.StringUtils;
import org.userbot.estateuserbot.utils.TDApiUtils;

public class DTOFactory {

    public static TelegramMessage messageFromUpdate(TdApi.UpdateNewMessage update) throws Exception {
        TdApi.Message message = update.message;
        String text = TDApiUtils.getOnlyText(update);
        long userId = TDApiUtils.getUserId(update);

        if( text == null || text.isEmpty() ) return  null;
        text = StringUtils.trimString(text);


//        if(userId == UserBotContextStore.getContext().id){
//            return null;
//        }

        return new TelegramMessage(message.id, text,0,0,userId,message.chatId);
    }


  

    public static UserInfo userInfoFromUser(TdApi.User user){

        return new UserInfo(user);

    }
    public static ChatInfo chatInfoFromChat(TdApi.Chat chat){
        return new ChatInfo(chat);
    }
}
