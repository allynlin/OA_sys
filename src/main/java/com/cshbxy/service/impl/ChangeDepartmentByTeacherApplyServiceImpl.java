package com.cshbxy.service.impl;

import com.cshbxy.dao.ChangeDepartmentByTeacher;
import com.cshbxy.mapper.ChangeDepartmentByTeacherApplyMapper;
import com.cshbxy.service.ChangeDepartmentByTeacherApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ApplyService")
public class ChangeDepartmentByTeacherApplyServiceImpl implements ChangeDepartmentByTeacherApplyService {

    @Autowired
    private ChangeDepartmentByTeacherApplyMapper changeDepartmentByTeacherApplyMapper;

    @Override
    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher) {
        return changeDepartmentByTeacherApplyMapper.teacherChangeDepartment(changeDepartmentByTeacher);
    }

    @Override
    public int checkTeacherChangeDepartment(String releaseUid) {
        return changeDepartmentByTeacherApplyMapper.checkTeacherChangeDepartment(releaseUid);
    }

    @Override
    public List<ChangeDepartmentByTeacher> findChangeDepartmentByTeacherList(String releaseUid) {
        return changeDepartmentByTeacherApplyMapper.findChangeDepartmentByTeacherList(releaseUid);
    }

    @Override
    public ChangeDepartmentByTeacher findChangeDepartmentByTeacher(String uid){
        return changeDepartmentByTeacherApplyMapper.findChangeDepartmentByTeacher(uid);
    }

    @Override
    public int deleteByUid(String uid){
        return changeDepartmentByTeacherApplyMapper.deleteByUid(uid);
    }
}
