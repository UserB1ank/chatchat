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

    private String nickname;
    private String avatar;
    private String motto;

}
