package com.cshbxy.mapper;

import com.cshbxy.domain.ChangeDepartmentByTeacher;

public interface ApplyMapper {

    public int teacherChangeDepartment(ChangeDepartmentByTeacher changeDepartmentByTeacher);

    public int checkTeacherChangeDepartment(String releaseUid);

}
