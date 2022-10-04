package com.cshbxy.mapper;

import com.cshbxy.dao.ChangeDepartmentByTeacher;

import java.util.List;

public interface ChangeDepartmentByTeacherApplyMapper {

    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher);

    public int checkTeacherChangeDepartment(String releaseUid);

    public List<ChangeDepartmentByTeacher> findChangeDepartmentByTeacherList(String releaseUid);

    public ChangeDepartmentByTeacher findChangeDepartmentByTeacher(String uid);

    public int deleteByUid(String uid);

}
