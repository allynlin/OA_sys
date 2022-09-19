package com.itheima.service.impl;

import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.mapper.QueryMapper;
import com.itheima.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("QueryService")
public class QueryServiceImpl implements QueryService {

    @Autowired
    private QueryMapper queryMapper;

    @Override
    public String teacherUsernameQuery(String username) {
        return queryMapper.teacherUsernameQuery(username);
    }

    @Override
    public String departmentUsernameQuery(String username) {
        return queryMapper.departmentUsernameQuery(username);
    }

    @Override
    public String leaderUsernameQuery(String username) {
        return queryMapper.leaderUsernameQuery(username);
    }

    @Override
    public List<Department> departmentMessage() {
        return queryMapper.departmentMessage();
    }

    @Override
    public List<Leader> leaderMessage() {
        return queryMapper.leaderMessage();
    }
}
