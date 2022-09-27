package com.cshbxy.service.impl;

import com.cshbxy.domain.FileName;
import com.cshbxy.mapper.FileUploadMapper;
import com.cshbxy.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Override
    public int addUploadFile(FileName fileName) {
        return fileUploadMapper.addUploadFile(fileName);
    }

    @Override
    public int dropUploadFile(String fileName) {
        return fileUploadMapper.dropUploadFile(fileName);
    }

    @Override
    public int updateUploadFile(FileName fileName) {
        return fileUploadMapper.updateUploadFile(fileName);
    }

    @Override
    public List<FileName> checkLastTimeUploadFiles(FileName fileName) {
        return fileUploadMapper.checkLastTimeUploadFiles(fileName);
    }
}
