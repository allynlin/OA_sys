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
    public int add(WorkReport workReport) {
        return workReportMapper.add(workReport);
    }

    @Override
    public WorkReport checkLastTime(String releaseUid) {
        return workReportMapper.checkLastTime(releaseUid);
    }

    @Override
    public List<WorkReport> findApplyList(String releaseUid) {
        return workReportMapper.findApplyList(releaseUid);
    }

    @Override
    public WorkReport findWorkReportByUid(String uid) {
        return workReportMapper.findWorkReportByUid(uid);
    }

    @Override
    public int delete(String uid) {
        return workReportMapper.delete(uid);
    }

    @Override
    public List<WorkReport> findWaitList(String nextUid) {
        return workReportMapper.findWaitList(nextUid);
    }

    @Override
    public int resolve(WorkReport workReport) {
        return workReportMapper.resolve(workReport);
    }

    @Override
    public int reject(WorkReport workReport) {
        return workReportMapper.reject(workReport);
    }
}
