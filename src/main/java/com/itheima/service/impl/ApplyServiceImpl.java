package com.itheima.service.impl;

import com.itheima.mapper.ApplyMapper;
import com.itheima.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ApplyService")
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public int teacherChangeDepartment(String uid, String releaseUid, String departmentUid, String changeReason, String changeFile) {
        return applyMapper.teacherChangeDepartment(uid, releaseUid, departmentUid, changeReason, changeFile);
    }
}
