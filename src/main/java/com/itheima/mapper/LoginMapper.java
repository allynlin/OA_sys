package com.itheima.mapper;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.domain.Teacher;
import org.apache.ibatis.annotations.Param;

public interface LoginMapper {

    public Teacher teacherLogin(@Param("username") String username, @Param("password") String password);

    public Department departmentLogin(@Param("username") String username, @Param("password") String password);

    public Leader leaderLogin(@Param("username") String username, @Param("password") String password);

}
