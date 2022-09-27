package com.cshbxy.service.impl;

import com.cshbxy.domain.ChangeDepartmentByTeacher;
import com.cshbxy.mapper.ApplyMapper;
import com.cshbxy.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ApplyService")
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher) {
        return applyMapper.teacherChangeDepartment(changeDepartmentByTeacher);
    }

    @Override
    public int checkTeacherChangeDepartment(String releaseUid) {
        return applyMapper.checkTeacherChangeDepartment(releaseUid);
    }
}
