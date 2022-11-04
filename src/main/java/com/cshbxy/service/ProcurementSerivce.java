package com.cshbxy.service;

import com.cshbxy.dao.Procurement;

import java.util.List;

public interface ProcurementSerivce {
    public int add(Procurement procurement);

    public int delete(String uid);

    public List<Procurement> findApplyList(String releaseUid);

    public Procurement findProcurementByUid(String uid);

    public int update(Procurement procurement);

    public List<Procurement> findWaitList(String nextUid);

    public int resolve(Procurement procurement);

    public int reject(Procurement procurement);
}
