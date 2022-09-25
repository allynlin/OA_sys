package com.cshbxy.service;

import com.cshbxy.domain.ChangeDepartmentByTeacher;

public interface ApplyService {

    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher);

    public int checkTeacherChangeDepartment(String releaseUid);
}