package com.cshbxy.service;

import com.cshbxy.dao.LoginRecord;

import java.util.List;

public interface LoginRecordService {

    int add(LoginRecord loginRecord);

    List<LoginRecord> findUserList(String userUid);
}