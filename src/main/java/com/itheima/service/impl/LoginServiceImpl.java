package com.itheima.service.impl;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.domain.Teacher;
import com.itheima.mapper.LoginMapper;
import com.itheima.service.LoginService;
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
