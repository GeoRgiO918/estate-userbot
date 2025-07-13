package org.userbot.dto;

import org.drinkless.tdlib.TdApi;

public class ChatInfo {
    private long id;
    private String title;

    public ChatInfo(long id){
        this.id = id;
        this.title = "Не задано";
    }
    public ChatInfo(TdApi.Chat chat){
        id =chat.id;
        title = chat.title;
        if(chat.type instanceof TdApi.ChatTypePrivate){
            title = "Личные сообщения";
        }
    }

    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

}
