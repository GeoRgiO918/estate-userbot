package org.userbot.estateuserbot;

import org.drinkless.tdlib.TdApi;
import org.userbot.estateuserbot.component.TelegramClient;

public class SearchService {

    private  TelegramClient client;

    public SearchService(){

    }

    public  TdApi.User searchUserbyId(long userId){
        TdApi.GetUser getUserRequest = new TdApi.GetUser(userId);
        Object response;
        try {
            response =client.sendSync(getUserRequest);
        }catch (Exception e){
            System.out.println("Error while searching for User: "+ userId + " " + e.getLocalizedMessage());
            return null;
        }
        return (TdApi.User)response;
    }

    public TdApi.Chat searchChatByUsername(String username){
        TdApi.SearchPublicChat search = new TdApi.SearchPublicChat(username);
        return searchChat(search);
    }

    public TdApi.Chat searchChatById(long chatId) {
        TdApi.GetChat search = new TdApi.GetChat(chatId);
        return searchChat(search);

    }
    private TdApi.Chat searchChat(TdApi.Function<TdApi.Chat> chatRequest){
            Object response;

            try {
                response =client.sendSync(chatRequest);
            }catch (Exception e){
                System.out.println("Error while searching for Chat: "+ chatRequest.toString() + " " + e.getLocalizedMessage());
                return null;
            }
            if (response instanceof TdApi.Chat) {
                return (TdApi.Chat)response;
            }
            return null;
        }
}

