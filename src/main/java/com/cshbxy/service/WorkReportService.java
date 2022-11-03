package com.cshbxy.service;

import com.cshbxy.dao.WorkReport;

import java.util.List;

public interface WorkReportService {
    public int addWorkReport(WorkReport workReport);

    public WorkReport checkLastWeekWorkReport(String releaseUid);

    public List<WorkReport> findWorkReportList(String releaseUid);

    public WorkReport findWorkReportByUid(String uid);

    public int deleteByUid(String uid);

    public List<WorkReport> findWorkReportWaitList(String nextUid);

    public int resolveWorkReport(WorkReport workReport);

    public int rejectWorkReport(WorkReport workReport);
}
