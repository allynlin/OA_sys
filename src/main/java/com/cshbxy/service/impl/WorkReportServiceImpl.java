package com.cshbxy.service.impl;

import com.cshbxy.dao.WorkReport;
import com.cshbxy.mapper.WorkReportMapper;
import com.cshbxy.service.WorkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("WorkReportService")
public class WorkReportServiceImpl implements WorkReportService {

    @Autowired
    private WorkReportMapper workReportMapper;

    @Override
    public int addWorkReport(WorkReport workReport) {
        return workReportMapper.addWorkReport(workReport);
    }

    @Override
    public WorkReport checkLastWeekWorkReport(String releaseUid) {
        return workReportMapper.checkLastWeekWorkReport(releaseUid);
    }

    @Override
    public List<WorkReport> findWorkReportList(String releaseUid) {
        return workReportMapper.findWorkReportList(releaseUid);
    }

    @Override
    public WorkReport findWorkReportByUid(String uid) {
        return workReportMapper.findWorkReportByUid(uid);
    }

    @Override
    public int deleteByUid(String uid) {
        return workReportMapper.deleteByUid(uid);
    }
}
