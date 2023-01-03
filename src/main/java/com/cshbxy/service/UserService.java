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

    public List<User> findAllUser();

    public List<User> findAllUserByDepartmentUid(String departmentUid);

    public List<User> findProcessUser();

    public String findDepartmentKey(String departmentUid);

    public List<User> findAllLeaderByDepartmentUid(String departmentUid);

    public int updateDepartmentLeader(User user);

    public int deleteDepartmentKey(String departmentUid);
}