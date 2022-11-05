package com.cshbxy.service;

import com.cshbxy.dao.User;

import java.util.List;


public interface UserService {

    public User login(User user);

    public String findRealeName(String uid);

    public String checkUsername(User user);

    public int add(User user);

    public int update(User user);

    public int delete(String uid);

    public User findUserByUid(String uid);

    public int updatePassword(User user);

    public int updateUsername(User user);

    public int updateStatus(User user);

    public List<User> findUserType();

    public String findDepartmentUid(String uid);

    public int updateDepartment(User user);
}