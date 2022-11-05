package com.cshbxy.mapper;

import com.cshbxy.dao.DepartmentChange;

import java.util.List;

public interface DepartmentChangeMapper {

    int add(DepartmentChange departmentChange);

    int checkLastTime(String releaseUid);

    List<DepartmentChange> findApplyList(String releaseUid);

    DepartmentChange findDepartmentChangeByUid(String uid);

    int delete(String uid);

    List<DepartmentChange> findWaitList(String nextUid);

    int resolve(DepartmentChange departmentChange);

    int reject(DepartmentChange departmentChange);

}
