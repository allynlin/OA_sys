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
    public int addProcurement(Procurement procurement) {
        return procurementMapper.addProcurement(procurement);
    }

    @Override
    public int deleteProcurement(String uid) {
        return procurementMapper.deleteProcurement(uid);
    }

    @Override
    public List<Procurement> findProcurementList(String releaseUid) {
        return procurementMapper.findProcurementList(releaseUid);
    }

    @Override
    public Procurement findProcurementByUid(String uid) {
        return procurementMapper.findProcurementByUid(uid);
    }

    @Override
    public int updateProcurement(Procurement procurement) {
        return procurementMapper.updateProcurement(procurement);
    }
}
