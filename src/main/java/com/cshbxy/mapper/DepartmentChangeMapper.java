package com.cshbxy.mapper;

import com.cshbxy.dao.DepartmentChange;

import java.util.List;

public interface DepartmentChangeMapper {

    public int add(DepartmentChange departmentChange);

    public int checkLastTime(String releaseUid);

    public List<DepartmentChange> findApplyList(String releaseUid);

    public DepartmentChange findDepartmentChangeByUid(String uid);

    public int delete(String uid);

    public List<DepartmentChange> findWaitList(String nextUid);

    public int resolve(DepartmentChange departmentChange);

    public int reject(DepartmentChange departmentChange);

}
