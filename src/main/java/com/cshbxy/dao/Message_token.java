package com.cshbxy.dao;

public class Message_token {
    private int code;
    private String msg;
    private String token;

    public Message_token(int code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public Message_token(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.token = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Message_token{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
