package com.cshbxy.service;

import com.cshbxy.dao.TravelReimbursement;

import java.util.List;

public interface TravelReimbursementApplyService {

    // 添加差旅报销审批
    public int addTravelReimbursement(TravelReimbursement travelReimbursement);

    // 查询差旅报销申请
    public List<TravelReimbursement> findTravelReimbursementApplyList(TravelReimbursement travelReimbursement);

    // 修改差旅报销
    public int updateTravelReimbursementApply(TravelReimbursement travelReimbursement);

    // 删除差旅报销申请
    public int deleteTravelReimbursementApply(String uid);

    public TravelReimbursement findTravelProcess(String uid);
}