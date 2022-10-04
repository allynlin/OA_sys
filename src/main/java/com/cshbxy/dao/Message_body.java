package com.cshbxy.dao;

public class Message_body {
    private int code;
    private String msg;
    private Object body;

    public Message_body(int code, String msg, Object o) {
        this.code = code;
        this.msg = msg;
        this.body = o;
    }

    public Message_body(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.body = null;
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

    @Override
    public String toString() {
        return "Message_body{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", body=" + body +
                '}';
    }
}
