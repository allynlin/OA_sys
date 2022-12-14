package com.cshbxy.dao;

public class User {
    private String uid;
    private String username;
    private String password;
    private String realeName;
    private String gender;
    private String tel;
    private String email;
    private String userType;
    private String departmentUid;
    private int status;
    private int departmentKey;
    private String create_time;
    private String update_time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealeName() {
        return realeName;
    }

    public void setRealeName(String realeName) {
        this.realeName = realeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDepartmentUid() {
        return departmentUid;
    }

    public void setDepartmentUid(String departmentUid) {
        this.departmentUid = departmentUid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(int departmentKey) {
        this.departmentKey = departmentKey;
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
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realeName='" + realeName + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", departmentUid='" + departmentUid + '\'' +
                ", status=" + status +
                ", departmentKey=" + departmentKey +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
