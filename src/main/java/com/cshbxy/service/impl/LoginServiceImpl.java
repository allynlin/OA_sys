package com.cshbxy.service.impl;

import com.cshbxy.domain.Department;
import com.cshbxy.domain.Leader;
import com.cshbxy.domain.Teacher;
import com.cshbxy.mapper.LoginMapper;
import com.cshbxy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Teacher teacherLogin(String username, String password) {
        return loginMapper.teacherLogin(username, password);
    }

    @Override
    public Department departmentLogin(String username, String password) {
        return loginMapper.departmentLogin(username, password);
    }

    @Override
    public Leader leaderLogin(String username, String password) {
        return loginMapper.leaderLogin(username, password);
    }
}
