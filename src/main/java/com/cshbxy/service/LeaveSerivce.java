package com.cshbxy.service;

import com.cshbxy.dao.Leave;

import java.util.List;

public interface LeaveSerivce {
    public int add(Leave leave);


    public int delete(String uid);

    public List<Leave> findApplyList(String releaseUid);

    public Leave findLeaveByUid(String uid);

    public int update(Leave leave);

    public List<Leave> findWaitList(String nextUid);

    public int resolve(Leave leave);

    public int reject(Leave leave);
}
