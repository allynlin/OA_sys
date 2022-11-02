package com.cshbxy.service;

import com.cshbxy.dao.Teacher;

public interface TeacherService {

    public String findDepartmentUid(String uid);

    public String findRealeName(String uid);

    public int updateDepartment(Teacher teacher);
}