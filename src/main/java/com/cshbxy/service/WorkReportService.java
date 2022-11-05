package com.cshbxy.service;

import com.cshbxy.dao.WorkReport;

import java.util.List;

public interface WorkReportService {
    public int add(WorkReport workReport);

    public WorkReport checkLastTime(String releaseUid);

    public List<WorkReport> findApplyList(String releaseUid);

    public WorkReport findWorkReportByUid(String uid);

    public int delete(String uid);

    public List<WorkReport> findWaitList(String nextUid);

    public int resolve(WorkReport workReport);

    public int reject(WorkReport workReport);
}
