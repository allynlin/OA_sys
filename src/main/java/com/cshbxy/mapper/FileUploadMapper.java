package com.cshbxy.mapper;

import com.cshbxy.dao.FileName;

import java.util.List;

public interface FileUploadMapper {

    public int addUploadFile(FileName fileName);

    public int dropUploadFile(String uid);

    public int dropUploadFileByRowUid(FileName fileName);

    public int updateUploadFile(FileName fileName);

    public List<FileName> checkLastTimeUploadFiles(FileName fileName);

    public String findFileOldNameByFileName(String fileName);

    public List<FileName> findUploadFilesByUid(FileName fileName);

}
