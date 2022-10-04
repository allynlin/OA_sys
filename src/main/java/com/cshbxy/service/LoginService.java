package com.cshbxy.service;

import com.cshbxy.dao.Department;
import com.cshbxy.dao.Leader;
import com.cshbxy.dao.Teacher;

public interface LoginService {

    public Teacher teacherLogin(String username, String password);

    public Department departmentLogin(String username, String password);

    public Leader leaderLogin(String username, String password);

}
