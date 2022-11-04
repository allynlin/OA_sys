package com.cshbxy.mapper;

import com.cshbxy.dao.Travel;

import java.util.List;

public interface TravelMapper {

    // 添加差旅报销审批
    public int add(Travel travel);

    // 查询差旅报销申请
    public List<Travel> findApplyList(String releaseUid);

    // 删除差旅报销申请
    public int delete(String uid);

    // 查询审批流程
    public Travel findTravelByUid(String uid);

    public List<Travel> findWaitList(String nextUid);

    public int resolve(Travel travel);

    public int reject(Travel travel);

}
