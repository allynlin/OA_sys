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
    public int addLeave(Leave leave) {
        return leaveMapper.addLeave(leave);
    }

    @Override
    public Leave checkLastTimeLeave(String releaseUid) {
        return leaveMapper.checkLastTimeLeave(releaseUid);
    }

    @Override
    public int deleteLeave(String uid) {
        return leaveMapper.deleteLeave(uid);
    }

    @Override
    public List<Leave> findLeaveList(String releaseUid) {
        return leaveMapper.findLeaveList(releaseUid);
    }

    @Override
    public Leave findLeaveByUid(String uid) {
        return leaveMapper.findLeaveByUid(uid);
    }

    @Override
    public int updateLeave(Leave leave) {
        return leaveMapper.updateLeave(leave);
    }
}
