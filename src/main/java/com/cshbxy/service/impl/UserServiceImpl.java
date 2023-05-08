package com.cshbxy.service.impl;

import com.cshbxy.dao.User;
import com.cshbxy.mapper.UserMapper;
import com.cshbxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public String findRealeName(String uid) {
        return userMapper.findRealeName(uid);
    }

    @Override
    public String checkUsername(User user) {
        return userMapper.checkUsername(user);
    }

    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(String uid) {
        return userMapper.delete(uid);
    }

    @Override
    public int updatePassword(User user) {
        return userMapper.updatePassword(user);
    }

    @Override
    public int updateUsername(User user) {
        return userMapper.updateUsername(user);
    }

    @Override
    public int updateStatus(User user) {
        return userMapper.updateStatus(user);
    }

    @Override
    public List<User> findUserType() {
        return userMapper.findUserType();
    }

    @Override
    public String findDepartmentUid(String uid) {
        return userMapper.findDepartmentUid(uid);
    }

    @Override
    public int updateDepartment(User user) {
        return userMapper.updateDepartment(user);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public List<User> findAllUserByDepartmentUid(String departmentUid) {
        return userMapper.findAllUserByDepartmentUid(departmentUid);
    }

    @Override
    public List<User> findProcessUser() {
        return userMapper.findProcessUser();
    }

    @Override
    public String findDepartmentKey(String departmentUid) {
        return userMapper.findDepartmentKey(departmentUid);
    }

    @Override
    public List<User> findAllLeaderByDepartmentUid(String departmentUid) {
        return userMapper.findAllLeaderByDepartmentUid(departmentUid);
    }

    @Override
    public int updateDepartmentLeader(User user) {
        return userMapper.updateDepartmentLeader(user);
    }

    @Override
    public int deleteDepartmentKey(String departmentUid) {
        return userMapper.deleteDepartmentKey(departmentUid);
    }
}
