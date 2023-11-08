package com.example.chatchat.data;

import com.example.chatchat.data.mysql.model.User;

public class Friend {
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Friend() {
    }

    public Friend(String account,String nickname, String avatar, String motto) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.motto = motto;
        this.account = account;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String account;

    private String nickname;
    private String avatar;
    private String motto;

}
