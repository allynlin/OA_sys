package com.itheima.service.impl;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.domain.Teacher;
import com.itheima.mapper.RegisterMapper;
import com.itheima.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RegisterService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    @Override
    public int teacherRegister(Teacher teacher) {
        return registerMapper.teacherRegister(teacher);
    }

    @Override
    public int departmentRegister(Department department) {
        return registerMapper.departmentRegister(department);
    }

    @Override
    public int leaderRegister(Leader leader) {
        return registerMapper.leaderRegister(leader);
    }
}
