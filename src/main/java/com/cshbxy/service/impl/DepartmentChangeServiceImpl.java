package com.cshbxy.service.impl;

import com.cshbxy.dao.DepartmentChange;
import com.cshbxy.mapper.DepartmentChangeMapper;
import com.cshbxy.service.DepartmentChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DepartmentChangeService")
public class DepartmentChangeServiceImpl implements DepartmentChangeService {

    @Autowired
    private DepartmentChangeMapper departmentChangeMapper;

    @Override
    public int add(DepartmentChange departmentChange) {
        return departmentChangeMapper.add(departmentChange);
    }

    @Override
    public int checkLastTime(String releaseUid) {
        return departmentChangeMapper.checkLastTime(releaseUid);
    }

    @Override
    public List<DepartmentChange> findApplyList(String releaseUid) {
        return departmentChangeMapper.findApplyList(releaseUid);
    }

    @Override
    public DepartmentChange findDepartmentChangeByUid(String uid) {
        return departmentChangeMapper.findDepartmentChangeByUid(uid);
    }

    @Override
    public int delete(String uid) {
        return departmentChangeMapper.delete(uid);
    }

    @Override
    public List<DepartmentChange> findWaitList(String nextUid) {
        return departmentChangeMapper.findWaitList(nextUid);
    }

    @Override
    public int resolve(DepartmentChange departmentChange) {
        return departmentChangeMapper.resolve(departmentChange);
    }

    @Override
    public int reject(DepartmentChange departmentChange) {
        return departmentChangeMapper.reject(departmentChange);
    }
}
