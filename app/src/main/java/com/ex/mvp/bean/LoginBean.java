package com.ex.mvp.bean;

/**
 * Author:  Jerry
 * Date:    2018/11/13
 * Version   v1.0
 * Desc:
 */
@SuppressWarnings("all")
public class LoginBean {
    private int uid;
    private String nickname;
    private String token;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
