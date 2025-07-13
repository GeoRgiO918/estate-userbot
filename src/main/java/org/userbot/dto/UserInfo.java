package org.userbot.dto;

import org.drinkless.tdlib.TdApi;

public class UserInfo {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;

    public UserInfo(long id) {
        this.id = id;
    }
    public UserInfo(TdApi.User user){
        id = user.id;
        firstName = user.firstName;
        lastName = user.lastName;
        if(user.usernames != null) username = user.usernames.activeUsernames[0];
        phoneNumber = user.phoneNumber;
    }

    public String getDisplayName() {
        return String.format(
                "%s %s (@%s, %s)",
                firstName != null ? firstName : "",
                lastName != null ? lastName : "",
                username != null ? username : "unknown",
                phoneNumber != null && !phoneNumber.isEmpty() ? "+" + phoneNumber : "no phone"
        );
    }


    public long getId() {
        return id;
    }
}
