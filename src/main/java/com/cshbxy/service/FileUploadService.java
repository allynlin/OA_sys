package com.cshbxy.service;

import com.cshbxy.dao.FileName;

import java.util.List;

public interface FileUploadService {

    public int addUploadFile(FileName fileName);

    public int dropUploadFile(String uid);

    public int updateUploadFile(FileName fileName);

    public List<FileName> checkLastTimeUploadFiles(FileName fileName);

    public String findFileOldNameByFileName(String fileName);

    public List<FileName> findUploadFilesByUid(FileName fileName);

    public String findFileCreateTimeByFileName(String fileName);

}
