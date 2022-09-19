package com.itheima.mapper;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;

import java.util.List;

public interface QueryMapper {

    public String teacherUsernameQuery(String username);

    public String departmentUsernameQuery(String username);

    public String leaderUsernameQuery(String username);

    public List<Department> departmentMessage();

    public List<Leader> leaderMessage();
}
