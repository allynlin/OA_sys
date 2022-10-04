package com.cshbxy.service;

import com.cshbxy.dao.ChangeDepartmentByTeacher;

import java.util.List;

public interface ChangeDepartmentByTeacherApplyService {

    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher);

    public int checkTeacherChangeDepartment(String releaseUid);

    public List<ChangeDepartmentByTeacher> findChangeDepartmentByTeacherList(String releaseUid);

    public ChangeDepartmentByTeacher findChangeDepartmentByTeacher(String uid);

    public int deleteByUid(String uid);
}