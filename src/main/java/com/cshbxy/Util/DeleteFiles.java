package com.cshbxy.Util;

import com.cshbxy.dao.FileName;
import com.cshbxy.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DeleteFiles {

    private static FileUploadService fileUploadService;

    @Autowired
    public void setFileUploadService(FileUploadService fileUploadService) {
        DeleteFiles.fileUploadService = fileUploadService;
    }

    /**
     * 关联上传文件
     *
     * @param uid
     * @param tableUid
     * @param releaseUid
     * @return 1:成功 0:失败
     */
    public static boolean updateFiles(String uid, String tableUid, String releaseUid) {
        FileName fileName = new FileName();
        fileName.setRowUid(uid);
        fileName.setTableUid(tableUid);
        fileName.setReleaseUid(releaseUid);
        int i = fileUploadService.updateUploadFile(fileName);
        return i > 0;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return 1:成功 0:失败
     */
    public static boolean deleteFiles(String fileName) {
        // 在 upload 目录下的所有文件夹中找到文件，删除
        File file = new File("C:\\upload\\");
        File[] files = file.listFiles();
        assert files != null;
        for (File file1 : files) {
            File[] files1 = file1.listFiles();
            assert files1 != null;
            for (File file2 : files1) {
                if (file2.getName().equals(fileName)) {
                    file2.delete();
                    break;
                }
            }
        }
        // 将文件名后缀去除
        String uid = fileName.substring(0, fileName.lastIndexOf("."));
        // 删除数据库中的文件名
        int i = fileUploadService.dropUploadFile(uid);
        return i > 0;
    }
}
