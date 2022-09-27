package com.cshbxy.controller;

import com.cshbxy.Util.CheckToken;
import com.cshbxy.Util.JwtUtil;
import com.cshbxy.domain.FileName;
import com.cshbxy.domain.Message;
import com.cshbxy.service.FileUploadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
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
                // 以当前日期创建一个文件夹，避免单个文件夹中文件过多
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                // 截取年月日：2020-11-02
                String substring = timestamp.toString().substring(0, 10);
                //获取存放文件在服务器中的路径,C 盘下的 upload 文件夹
                String realPath = "C:\\upload\\" + substring;
                //判断文件夹是否存在，不存在则创建
                File file1 = new File(realPath);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                // 获取文件名
                String upFileName = file.getOriginalFilename();
                // 获取 UUID，防止文件名重复，并设置数据表主键
                String uuid = UUID.randomUUID().toString();
                // 获取文件后缀名
                String suffixName =
                        Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
                // 拼接新文件名
                String fileName = uuid + suffixName;
                // 转存文件
                file.transferTo(new File(realPath + "\\" + fileName));
                // 存储文件名到数据库
                FileName fn = new FileName();
                fn.setUid(uuid);
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
            if (i != 1) {
                return new Message(400, "文件删除失败");
            }
            return new Message(200, "文件删除成功");
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

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,
                                               String filename) throws Exception {
        // 指定要下载的文件所在路径,C:\\upload\\
        StringBuilder path = new StringBuilder("C:\\upload\\");
        // 遍历 upload 目录下的所有文件夹，找到文件
        File file = new File(path.toString());
        File[] files = file.listFiles();
        assert files != null;
        for (File file1 : files) {
            File[] files1 = file1.listFiles();
            assert files1 != null;
            for (File file2 : files1) {
                if (file2.getName().equals(filename)) {
                    path.append(file1.getName()).append("\\").append(filename);
                    break;
                }
            }
        }
        String oldFileName = fileUploadService.findFileOldNameByFileName(filename);
        // 创建该文件对象
        File file1 = new File(String.valueOf(path));
        filename = this.getFilename(request, oldFileName);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        // 通知浏览器以下载的方式打开文件
        headers.setContentDispositionFormData("attachment", filename);
        // 定义以流的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用 Sring MVC 框架的 ResponseEntity 对象封装返回下载数据
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file1),
                headers, HttpStatus.OK);
    }

    /**
     * 根据浏览器的不同进行编码设置，返回编码后的文件名
     */
    public String getFilename(HttpServletRequest request,
                              String filename) throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : IEBrowserKeyWords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(filename, "UTF-8");
            }
        }
        //火狐等其它浏览器统一为ISO-8859-1编码显示
        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

}