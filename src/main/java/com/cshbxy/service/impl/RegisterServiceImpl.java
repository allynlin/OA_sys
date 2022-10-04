package com.cshbxy.service.impl;

import com.cshbxy.dao.Department;
import com.cshbxy.dao.Leader;
import com.cshbxy.dao.Teacher;
import com.cshbxy.mapper.RegisterMapper;
import com.cshbxy.service.RegisterService;
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
