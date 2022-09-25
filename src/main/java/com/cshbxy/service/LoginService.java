package com.cshbxy.service;

import com.cshbxy.domain.Department;
import com.cshbxy.domain.Leader;
import com.cshbxy.domain.Teacher;

public interface LoginService {

    public Teacher teacherLogin(String username, String password);

    public Department departmentLogin(String username, String password);

    public Leader leaderLogin(String username, String password);

}
