package com.cshbxy.mapper;

import com.cshbxy.dao.Department;
import com.cshbxy.dao.Leader;

import java.util.List;

public interface QueryMapper {

    public String teacherUsernameQuery(String username);

    public String departmentUsernameQuery(String username);

    public String leaderUsernameQuery(String username);

    public List<Department> departmentMessage();

    public List<Leader> leaderMessage();
}
