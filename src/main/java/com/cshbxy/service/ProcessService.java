package com.cshbxy.service;

import com.cshbxy.dao.Process;

import java.util.List;

public interface ProcessService {

    public Process processQueryByName(String name);

    public int processUpdate(Process process);

    public int processAliasUpdate(Process process);

    public List<Process> findAllProcess();
}
