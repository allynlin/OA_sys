package com.cshbxy.service.impl;

import com.cshbxy.dao.Procurement;
import com.cshbxy.mapper.ProcurementMapper;
import com.cshbxy.service.ProcurementSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProcurementSerivce")
public class ProcurementServiceImpl implements ProcurementSerivce {

    @Autowired
    private ProcurementMapper procurementMapper;

    @Override
    public int add(Procurement procurement) {
        return procurementMapper.add(procurement);
    }

    @Override
    public int delete(String uid) {
        return procurementMapper.delete(uid);
    }

    @Override
    public List<Procurement> findApplyList(String releaseUid) {
        return procurementMapper.findApplyList(releaseUid);
    }

    @Override
    public Procurement findProcurementByUid(String uid) {
        return procurementMapper.findProcurementByUid(uid);
    }

    @Override
    public int update(Procurement procurement) {
        return procurementMapper.update(procurement);
    }

    @Override
    public List<Procurement> findWaitList(String nextUid) {
        return procurementMapper.findWaitList(nextUid);
    }

    @Override
    public int resolve(Procurement procurement) {
        return procurementMapper.resolve(procurement);
    }

    @Override
    public int reject(Procurement procurement) {
        return procurementMapper.reject(procurement);
    }
}
