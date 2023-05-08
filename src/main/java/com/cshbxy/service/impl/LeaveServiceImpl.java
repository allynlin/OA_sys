package com.cshbxy.service.impl;

import com.cshbxy.dao.Leave;
import com.cshbxy.mapper.LeaveMapper;
import com.cshbxy.service.LeaveSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LeaveService")
public class LeaveServiceImpl implements LeaveSerivce {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public int add(Leave leave) {
        return leaveMapper.add(leave);
    }


    @Override
    public int delete(String uid) {
        return leaveMapper.delete(uid);
    }

    @Override
    public List<Leave> findApplyList(String releaseUid) {
        return leaveMapper.findApplyList(releaseUid);
    }

    @Override
    public Leave findLeaveByUid(String uid) {
        return leaveMapper.findLeaveByUid(uid);
    }

    @Override
    public int update(Leave leave) {
        return leaveMapper.update(leave);
    }

    @Override
    public List<Leave> findWaitList(String nextUid) {
        return leaveMapper.findWaitList(nextUid);
    }

    @Override
    public int resolve(Leave leave) {
        return leaveMapper.resolve(leave);
    }

    @Override
    public int reject(Leave leave) {
        return leaveMapper.reject(leave);
    }
}
