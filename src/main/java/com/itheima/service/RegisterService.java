package com.itheima.service;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.domain.Teacher;

public interface RegisterService {

    public int teacherRegister(Teacher teacher);

    public int departmentRegister(Department department);

    public int leaderRegister(Leader leader);

}
