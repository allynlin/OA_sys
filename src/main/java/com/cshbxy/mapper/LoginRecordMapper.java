package com.cshbxy.mapper;

import com.cshbxy.dao.LoginRecord;

import java.util.List;

public interface LoginRecordMapper {

    int add(LoginRecord loginRecord);

    List<LoginRecord> findUserList(String userUid);

}
