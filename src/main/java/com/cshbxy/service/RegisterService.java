package com.cshbxy.service;

import com.cshbxy.domain.Department;
import com.cshbxy.domain.Leader;
import com.cshbxy.domain.Teacher;

public interface RegisterService {

    public int teacherRegister(Teacher teacher);

    public int departmentRegister(Department department);

    public int leaderRegister(Leader leader);

}
