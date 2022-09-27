package com.cshbxy.mapper;

import com.cshbxy.domain.FileName;

import java.util.List;

public interface FileUploadMapper {

    public int addUploadFile(FileName fileName);

    public int dropUploadFile(String uid);

    public int updateUploadFile(FileName fileName);

    public List<FileName> checkLastTimeUploadFiles(FileName fileName);

    public String findFileOldNameByFileName(String fileName);

}
