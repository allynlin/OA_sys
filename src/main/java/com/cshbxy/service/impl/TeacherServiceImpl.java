package com.cshbxy.service.impl;

import com.cshbxy.mapper.TeacherMapper;
import com.cshbxy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public String findDepartmentUid(String uid) {
        return teacherMapper.findDepartmentUid(uid);
    }

    @Override
    public String findRealeName(String uid) {
        return teacherMapper.findRealeName(uid);
    }
}
