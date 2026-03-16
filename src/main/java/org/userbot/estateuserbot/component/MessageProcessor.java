package org.userbot.estateuserbot.component;

import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Component;
import org.userbot.estateuserbot.*;
import org.userbot.estateuserbot.component.classification.LeadScoringEngine;
import org.userbot.estateuserbot.dto.ChatInfo;
import org.userbot.estateuserbot.dto.TelegramMessage;
import org.userbot.estateuserbot.dto.UserInfo;
import org.userbot.estateuserbot.service.AdminNotificationService;

import java.time.LocalTime;

@Component
public class MessageProcessor {

    private LeadScoringEngine scoringEngine;
    private SearchService search;
    private AdminNotificationService notifyAdmin;

    public MessageProcessor(){
        scoringEngine = new LeadScoringEngine();
        search = new SearchService();
        notifyAdmin = new AdminNotificationService();
    }

     public void process(TdApi.UpdateNewMessage update){
        TelegramMessage message = extractMessage(update);
        if(message == null) return;
        scoringEngine.setScores(message);

        if (scoringEngine.isLead(message)) {
            enrichMessage(message);
            notifyAdmin(message);
            System.out.println("Message send " + LocalTime.now());
            FileThreadManager.sendToThread(message.getText());
        }
    }




    private TelegramMessage extractMessage(TdApi.UpdateNewMessage update){
        try {
            System.out.println("Message catched " + LocalTime.now());
            return DTOFactory.messageFromUpdate(update);

        }
        catch (Exception e){
            System.out.println("Error while creating message DTO " + e.getLocalizedMessage());
            return null;
        }
    }
    private void enrichMessage(TelegramMessage message){
        long chatId = message.getChat().getId();
        long userId = message.getSender().getId();

        ChatInfo chatInfo = DTOFactory.chatInfoFromChat(search.searchChatById(chatId));
        UserInfo userInfo = DTOFactory.userInfoFromUser(search.searchUserbyId(userId));

        message.setChat(chatInfo);
        message.setSender(userInfo);
    }
    private void notifyAdmin(TelegramMessage message){
        String adminUsername = " ";//ConfigStore.getConfig().getAdminUsername();
        TdApi.Chat adminChat = search.searchChatByUsername(adminUsername);
        notifyAdmin.notifyAdmin(message,adminChat);
    }
}

