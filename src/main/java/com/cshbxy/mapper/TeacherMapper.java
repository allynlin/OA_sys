package com.cshbxy.mapper;

import com.cshbxy.dao.Teacher;

public interface TeacherMapper {

    public String findDepartmentUid(String uid);

    public String findRealeName(String uid);

    public int updateDepartment(Teacher teacher);

}
