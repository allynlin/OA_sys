package com.cshbxy.dao;

public class Leave {
    private String uid;
    private String releaseUid;
    private String reason;
    private String start_time;
    private String end_time;
    private int status;
    private int count;
    private String nextUid;
    private String process;
    private String reject_reason;
    private String create_time;
    private String update_time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReleaseUid() {
        return releaseUid;
    }

    public void setReleaseUid(String releaseUid) {
        this.releaseUid = releaseUid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNextUid() {
        return nextUid;
    }

    public void setNextUid(String nextUid) {
        this.nextUid = nextUid;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
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
        return "Leave{" +
                "uid='" + uid + '\'' +
                ", releaseUid='" + releaseUid + '\'' +
                ", reason='" + reason + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", nextUid='" + nextUid + '\'' +
                ", process='" + process + '\'' +
                ", reject_reason='" + reject_reason + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
