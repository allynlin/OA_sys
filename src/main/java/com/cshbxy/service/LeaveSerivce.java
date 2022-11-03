package com.cshbxy.service;

import com.cshbxy.dao.Leave;

import java.util.List;

public interface LeaveSerivce {
    public int addLeave(Leave leave);

    public Leave checkLastTimeLeave(String releaseUid);

    public int deleteLeave(String uid);

    public List<Leave> findLeaveList(String releaseUid);

    public Leave findLeaveByUid(String uid);

    public int updateLeave(Leave leave);

    public List<Leave> findLeaveWaitList(String nextUid);

    public int resolveLeave(Leave leave);

    public int rejectLeave(Leave leave);
}
