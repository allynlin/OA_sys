package com.cshbxy.mapper;

import com.cshbxy.dao.Department;
import com.cshbxy.dao.Leader;
import com.cshbxy.dao.Teacher;
import org.apache.ibatis.annotations.Param;

public interface LoginMapper {

    public Teacher teacherLogin(@Param("username") String username, @Param("password") String password);

    public Department departmentLogin(@Param("username") String username, @Param("password") String password);

    public Leader leaderLogin(@Param("username") String username, @Param("password") String password);

}
