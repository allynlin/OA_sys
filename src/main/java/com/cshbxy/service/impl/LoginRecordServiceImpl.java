package com.cshbxy.service.impl;

import com.cshbxy.dao.LoginRecord;
import com.cshbxy.mapper.LoginRecordMapper;
import com.cshbxy.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LoginRecordService")
public class LoginRecordServiceImpl implements LoginRecordService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public int add(LoginRecord loginRecord) {
        return loginRecordMapper.add(loginRecord);
    }

    @Override
    public List<LoginRecord> findUserList(String userUid) {
        return loginRecordMapper.findUserList(userUid);
    }
}
