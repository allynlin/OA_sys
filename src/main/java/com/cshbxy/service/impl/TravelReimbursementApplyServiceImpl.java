package com.cshbxy.service.impl;

import com.cshbxy.dao.TravelReimbursement;
import com.cshbxy.mapper.TravelReimbursementApplyMapper;
import com.cshbxy.service.TravelReimbursementApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TravelReimbursementApplyService")
public class TravelReimbursementApplyServiceImpl implements TravelReimbursementApplyService {

    @Autowired
    private TravelReimbursementApplyMapper travelReimbursementApplyMapper;


    @Override
    public int addTravelReimbursement(TravelReimbursement travelReimbursement) {
        return travelReimbursementApplyMapper.addTravelReimbursement(travelReimbursement);
    }

    @Override
    public List<TravelReimbursement> findTravelReimbursementApplyList(TravelReimbursement travelReimbursement) {
        return travelReimbursementApplyMapper.findTravelReimbursementApplyList(travelReimbursement);
    }

    @Override
    public int updateTravelReimbursementApply(TravelReimbursement travelReimbursement) {
        return travelReimbursementApplyMapper.updateTravelReimbursementApply(travelReimbursement);
    }

    @Override
    public int deleteTravelReimbursementApply(String uid) {
        return travelReimbursementApplyMapper.deleteTravelReimbursementApply(uid);
    }

    @Override
    public TravelReimbursement findTravelProcess(String uid) {
        return travelReimbursementApplyMapper.findTravelProcess(uid);
    }
}
