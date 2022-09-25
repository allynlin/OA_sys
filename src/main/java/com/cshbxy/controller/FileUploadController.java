package com.cshbxy.controller;

import com.cshbxy.Util.CheckToken;
import com.cshbxy.Util.JwtUtil;
import com.cshbxy.domain.FileName;
import com.cshbxy.domain.Message;
import com.cshbxy.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Message uploadFile(HttpServletRequest request, MultipartFile file, HttpServletResponse response) {
        try {
            if (!JwtUtil.isTokenTrue(request.getHeader("Authorization"))) {
                return new Message(403, "身份验证失败");
            }
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String userUid = JwtUtil.getUserUid(token);
            String tableUid = request.getHeader("tableUid");
            // 处理中文乱码
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-type", "text/html;charset=utf-8");
            if (!file.isEmpty()) {
                // 获取文件名
                String upFileName = file.getOriginalFilename();
                // 重命名文件
                String fileName = UUID.randomUUID().toString();
                // 获取文件后缀名
                String suffixName =
                        Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
                // 拼接新文件名
                fileName = fileName + suffixName;
                // 看 D 盘是否有 upload 文件夹，没有就创建
                File fileDir = new File("D:\\upload");
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                // 文件保存路径
                String filePath = "D:\\upload\\" + fileName;
                // 转存文件
                file.transferTo(new File(filePath));
                // 存储文件名到数据库
                FileName fn = new FileName();
                fn.setUid(UUID.randomUUID().toString());
                fn.setReleaseUid(userUid);
                fn.setTableUid(tableUid);
                fn.setFileName(fileName);
                fn.setOldFileName(upFileName);
                int i = fileUploadService.addUploadFile(fn);
                if (i != 1) {
                    return new Message(400, "文件上传失败");
                }
                return new Message(200, upFileName + "文件上传成功", fileName);
            }
            return new Message(400, "文件上传失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/deleteUploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteUploadFile(HttpServletRequest request, String fileName) {
        try {
            if (!JwtUtil.isTokenTrue(request.getHeader("Authorization"))) {
                return new Message(403, "身份验证失败");
            }
            // 在 D 盘下的 upload 文件夹中删除文件
            File fileDir = new File("D:\\upload\\" + fileName);
            if (fileDir.exists()) {
                fileDir.delete();
            }
            int i = fileUploadService.dropUploadFile(fileName);
            if (i == 1) {
                return new Message(200, "文件删除成功");
            } else {
                return new Message(400, "文件删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/checkLastTimeUploadFiles", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkLastTimeUploadFiles(HttpServletRequest request) {
        try {
            if (!CheckToken.checkIsTeacher(request.getHeader("Authorization"))) {
                return new Message(403, "身份验证失败");
            }
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            String tableUid = request.getHeader("tableUid");
            FileName fileName = new FileName();
            fileName.setReleaseUid(releaseUid);
            fileName.setTableUid(tableUid);
            List<FileName> fileNames = fileUploadService.checkLastTimeUploadFiles(fileName);
            if (fileNames.size() > 0) {
                return new Message(200, "已获取到上次上传的文件列表", fileNames);
            } else {
                return new Message(300, "上次没有上传文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}