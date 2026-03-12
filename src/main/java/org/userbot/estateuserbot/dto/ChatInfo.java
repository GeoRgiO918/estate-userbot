package org.userbot.estateuserbot.dto;

import org.drinkless.tdlib.TdApi;

public class ChatInfo {
    private long id;
    private String title;
    private String username; // <-- добавлено

    public ChatInfo(long id) {
        this.id = id;
        this.title = "Не задано";
        this.username = null;
    }

    public ChatInfo(TdApi.Chat chat) {
        id = chat.id;
        title = chat.title;
        username = null;

        if (chat.type instanceof TdApi.ChatTypePrivate) {
            title = "Личные сообщения";
        } else if (chat.type instanceof TdApi.ChatTypeSupergroup) {
            TdApi.ChatTypeSupergroup type = (TdApi.ChatTypeSupergroup) chat.type;
            TdApi.Supergroup supergroup = getSupergroupDetails(type.supergroupId);
            if (supergroup != null && !supergroup.isChannel && supergroup.usernames != null && supergroup.usernames.activeUsernames != null) {
                username = supergroup.usernames.activeUsernames[0];
            }
        }
    }

    private TdApi.Supergroup getSupergroupDetails(long supergroupId) {
        try {
            TdApi.Supergroup supergroup = null;

//                    (TdApi.Supergroup) ClientStore.getClient().sendSync(
//                    new TdApi.GetSupergroup(supergroupId)
//            );
            return supergroup;
        } catch (Exception e) {
            System.out.println("Error while trying to get Supergroup: " + e.getLocalizedMessage());
            return null;
        }
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }
}

