package com.cshbxy.dao;

public class WorkReport {
    String uid;
    String releaseUid;
    int status;
    int count;
    String nextUid;
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
        return "WorkReport{" +
                "uid='" + uid + '\'' +
                ", releaseUid='" + releaseUid + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", nextUid='" + nextUid + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
