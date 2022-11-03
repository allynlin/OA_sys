package com.cshbxy.dao;

public class TravelReimbursement {
    String uid;
    String releaseUid;
    String destination;
    String expenses;
    String reason;
    int status;
    int count;
    String nextUid;
    String reject_reason;
    String create_time;
    String update_time;

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        return "TravelReimbursement{" +
                "uid='" + uid + '\'' +
                ", releaseUid='" + releaseUid + '\'' +
                ", destination='" + destination + '\'' +
                ", expenses='" + expenses + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", nextUid='" + nextUid + '\'' +
                ", reject_reason='" + reject_reason + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
