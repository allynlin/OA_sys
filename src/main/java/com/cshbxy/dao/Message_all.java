package com.cshbxy.dao;

public class Message_all {
    private int code;
    private String msg;
    private Object body;
    private String token;

    public Message_all(int code, String msg, Object o, String token) {
        this.code = code;
        this.msg = msg;
        this.body = o;
        this.token = token;
    }

    public Message_all(int code, String msg, Object o) {
        this.code = code;
        this.msg = msg;
        this.body = o;
        this.token = null;
    }

    public Message_all(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.body = null;
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

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", body=" + body +
                ", token='" + token + '\'' +
                '}';
    }
}
