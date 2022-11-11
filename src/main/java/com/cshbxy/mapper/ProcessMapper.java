package com.cshbxy.mapper;

import com.cshbxy.dao.Process;

import java.util.List;

public interface ProcessMapper {

    public Process processQueryByName(String name);

    public int processUpdate(Process process);

    public int processAliasUpdate(Process process);

    public List<Process> findAllProcess();
}
