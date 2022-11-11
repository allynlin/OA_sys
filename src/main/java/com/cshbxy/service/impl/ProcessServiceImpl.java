package com.cshbxy.service.impl;

import com.cshbxy.dao.Process;
import com.cshbxy.mapper.ProcessMapper;
import com.cshbxy.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProcessService")
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Override
    public Process processQueryByName(String name) {
        return processMapper.processQueryByName(name);
    }

    @Override
    public int processUpdate(Process process) {
        return processMapper.processUpdate(process);
    }

    @Override
    public int processAliasUpdate(Process process) {
        return processMapper.processAliasUpdate(process);
    }

    @Override
    public List<Process> findAllProcess() {
        return processMapper.findAllProcess();
    }
}
