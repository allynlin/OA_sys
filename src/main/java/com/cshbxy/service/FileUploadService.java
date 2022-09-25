package com.cshbxy.service;

import com.cshbxy.domain.FileName;

import java.util.List;

public interface FileUploadService {

    public int addUploadFile(FileName fileName);

    public int dropUploadFile(String fileName);

    public int updateUploadFile(FileName fileName);

    public List<FileName> checkLastTimeUploadFiles(FileName fileName);

}
