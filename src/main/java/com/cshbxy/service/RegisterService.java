package com.cshbxy.service;

import com.cshbxy.dao.Department;
import com.cshbxy.dao.Leader;
import com.cshbxy.dao.Teacher;

public interface RegisterService {

    public int teacherRegister(Teacher teacher);

    public int departmentRegister(Department department);

    public int leaderRegister(Leader leader);

}
