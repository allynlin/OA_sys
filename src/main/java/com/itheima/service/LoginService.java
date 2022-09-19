package com.itheima.service;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.domain.Teacher;

public interface LoginService {

    public Teacher teacherLogin(String username, String password);

    public Department departmentLogin(String username, String password);

    public Leader leaderLogin(String username, String password);

}
