package com.cshbxy.domain;

public class FileName {
    String uid;
    String ReleaseUid;
    String TableUid;
    String RowUid;
    String FileName;
    String oldFileName;
    String create_time;
    String update_time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReleaseUid() {
        return ReleaseUid;
    }

    public void setReleaseUid(String releaseUid) {
        ReleaseUid = releaseUid;
    }

    public String getTableUid() {
        return TableUid;
    }

    public void setTableUid(String tableUid) {
        TableUid = tableUid;
    }

    public String getRowUid() {
        return RowUid;
    }

    public void setRowUid(String rowUid) {
        RowUid = rowUid;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
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
        return "FileName{" +
                "uid='" + uid + '\'' +
                ", ReleaseUid='" + ReleaseUid + '\'' +
                ", TableUid='" + TableUid + '\'' +
                ", RowUid='" + RowUid + '\'' +
                ", FileName='" + FileName + '\'' +
                ", oldFileName='" + oldFileName + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
