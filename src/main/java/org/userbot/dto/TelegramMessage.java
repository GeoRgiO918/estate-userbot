package org.userbot.dto;

import org.drinkless.tdlib.TdApi;

public class TelegramMessage {
    private long messageId;
    private String text;
    private UserInfo sender;
    private int leadPotential;
    private  double leadProbability;
    private ChatInfo chat;

    public TelegramMessage(long messageId, String text, int leadPotential, double leadProbability, long senderId, long chatId) {
        this.messageId = messageId;
        this.text = text;
        this.sender = new UserInfo(senderId);
        this.leadPotential = leadPotential;
        this.leadProbability = leadProbability;
        this.chat = new ChatInfo(chatId);
    }

    public String getText() {
        return text;
    }

    public UserInfo getSender() {
        return sender;
    }

    public int getLeadPotential() {
        return leadPotential;
    }
    public double getLeadProbability(){
        return leadProbability;
    }
    public ChatInfo getChat(){
        return  chat;
    }
    public void setChat(ChatInfo chat){
        this.chat = chat;
    }

    public void setSender(UserInfo sender){
        this.sender = sender;
    }
    public void setLeadPotential(int leadPotential){
        this.leadPotential = leadPotential;
    }
    public void setLeadProbability(double leadProbability){
        this.leadProbability = leadProbability;
    }

}
