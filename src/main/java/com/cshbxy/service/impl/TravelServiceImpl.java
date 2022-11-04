package com.cshbxy.service.impl;

import com.cshbxy.dao.Travel;
import com.cshbxy.mapper.TravelMapper;
import com.cshbxy.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TravelReimbursementApplyService")
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelMapper travelMapper;


    @Override
    public int add(Travel travel) {
        return travelMapper.add(travel);
    }

    @Override
    public List<Travel> findApplyList(String releaseUid) {
        return travelMapper.findApplyList(releaseUid);
    }

    @Override
    public int delete(String uid) {
        return travelMapper.delete(uid);
    }

    @Override
    public Travel findTravelByUid(String uid) {
        return travelMapper.findTravelByUid(uid);
    }

    @Override
    public List<Travel> findWaitList(String nextUid) {
        return travelMapper.findWaitList(nextUid);
    }

    @Override
    public int resolve(Travel travel) {
        return travelMapper.resolve(travel);
    }

    @Override
    public int reject(Travel travel) {
        return travelMapper.reject(travel);
    }
}
