package com.cshbxy.mapper;

import com.cshbxy.dao.Procurement;

import java.util.List;

public interface ProcurementMapper {

    public int add(Procurement procurement);

    public int delete(String uid);

    public List<Procurement> findApplyList(String releaseUid);

    public Procurement findProcurementByUid(String uid);

    public int update(Procurement procurement);

    public List<Procurement> findWaitList(String nextUid);

    public int resolve(Procurement procurement);

    public int reject(Procurement procurement);
}
