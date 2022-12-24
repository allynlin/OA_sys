package com.cshbxy.dao;

public class Process {
    private String uid;
    private String name;
    private String process;
    private String processRealName;
    private int status;
    private String realeName;
    private String create_time;
    private String update_time;

    public String getRealeName() {
        return realeName;
    }

    public void setRealeName(String realeName) {
        this.realeName = realeName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getProcessRealName() {
        return processRealName;
    }

    public void setProcessRealName(String processRealName) {
        this.processRealName = processRealName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Process{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", process='" + process + '\'' +
                ", processRealName='" + processRealName + '\'' +
                ", status=" + status +
                ", realeName='" + realeName + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
