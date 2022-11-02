package com.cshbxy.mapper;

import com.cshbxy.dao.Procurement;

import java.util.List;

public interface ProcurementMapper {

    public int addProcurement(Procurement procurement);

    public int deleteProcurement(String uid);

    public List<Procurement> findProcurementList(String releaseUid);

    public Procurement findProcurementByUid(String uid);

    public int updateProcurement(Procurement procurement);
}
