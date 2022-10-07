package com.cshbxy.mapper;

import com.cshbxy.dao.WorkReport;

import java.util.List;

public interface WorkReportMapper {

    public int addWorkReport(WorkReport workReport);

    public WorkReport checkLastWeekWorkReport(String releaseUid);

    public List<WorkReport> findWorkReportList(String releaseUid);

    public WorkReport findWorkReportByUid(String uid);

    public int deleteByUid(String uid);

}