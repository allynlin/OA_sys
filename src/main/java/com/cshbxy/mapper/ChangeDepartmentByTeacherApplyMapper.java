package com.cshbxy.mapper;

import com.cshbxy.dao.ChangeDepartmentByTeacher;

import java.util.List;

public interface ChangeDepartmentByTeacherApplyMapper {

    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher);

    public int checkTeacherChangeDepartment(String releaseUid);

    public List<ChangeDepartmentByTeacher> findChangeDepartmentByTeacherList(String releaseUid);

    public ChangeDepartmentByTeacher findChangeDepartmentByTeacher(String uid);

    public int deleteByUid(String uid);

    public List<ChangeDepartmentByTeacher> findWaitList(String nextUid);

    public int resolveApply(ChangeDepartmentByTeacher changeDepartmentByTeacher);

    public int rejectApply(ChangeDepartmentByTeacher changeDepartmentByTeacher);

}
