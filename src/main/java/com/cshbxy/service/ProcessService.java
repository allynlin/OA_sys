package com.cshbxy.service;

import com.cshbxy.dao.Process;

public interface ProcessService {
    public Process processQueryByUid(String uid);

    public Process processQueryByName(String name);

    public int processUpdate(Process process);

    public int processInsert(Process process);

    public int processDelete(String uid);
}
