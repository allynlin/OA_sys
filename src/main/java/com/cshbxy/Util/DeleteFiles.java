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

    /**
     * 隐藏文件
     *
     * @param fileName
     * @return 1:成功 0:失败
     */
    public static boolean hideFiles(String fileName) {
        // 根据文件名查找对应记录
        String create_time = fileUploadService.findFileCreateTimeByFileName(fileName);
        // 分离 create_time 中的日期 yyyy-MM-dd
        String substring = create_time.substring(0, 10);
        // 在路径后面拼接日期
        String path = "C:\\upload\\" + substring + "\\" + fileName;
        // 将文件移动到隐藏文件夹
        // 如果没有隐藏文件夹则创建
        File hide = new File("C:\\upload\\hide");
        if (!hide.exists()) {
            hide.mkdir();
        }
        File file = new File(path);
        File file1 = new File("C:\\upload\\hide\\" + fileName);
        boolean b = file.renameTo(file1);
        // 将文件名后缀去除
        String uid = fileName.substring(0, fileName.lastIndexOf("."));
        // 删除数据库中的文件名
        fileUploadService.dropUploadFile(uid);
        return b;
    }
}
