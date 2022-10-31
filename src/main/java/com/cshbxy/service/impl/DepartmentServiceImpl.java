package com.cshbxy.service.impl;

import com.cshbxy.mapper.DepartmentMapper;
import com.cshbxy.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DepartmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public String findRealeName(String uid) {
        return departmentMapper.findRealeName(uid);
    }
}
