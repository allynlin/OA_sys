package com.cshbxy.controller;

import com.cshbxy.Util.CheckToken;
import com.cshbxy.Util.JwtUtil;
import com.cshbxy.domain.ChangeDepartmentByTeacher;
import com.cshbxy.domain.FileName;
import com.cshbxy.domain.Message;
import com.cshbxy.service.ApplyService;
import com.cshbxy.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/apply")
@CrossOrigin(origins = "*")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private FileUploadService fileUploadService;

    public boolean updateFiles(String uid, String tableUid, String releaseUid) {
        FileName fileName = new FileName();
        fileName.setRowUid(uid);
        fileName.setTableUid(tableUid);
        fileName.setReleaseUid(releaseUid);
        int i = fileUploadService.updateUploadFile(fileName);
        return i > 0;
    }

    @RequestMapping(value = "/teacherChangeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message teacherChangeDepartment(HttpServletRequest request, ChangeDepartmentByTeacher changeDepartmentByTeacher) {
        try {
            if (!CheckToken.checkIsTeacher(request.getHeader("Authorization"))) {
                return new Message(403, "身份验证失败");
            }
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            String tableUid = request.getHeader("tableUid");
            int i = applyService.checkTeacherChangeDepartment(releaseUid);
            if (i > 0) {
                return new Message(401, "您已提交过部门变更申请，请勿重复提交");
            }
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            changeDepartmentByTeacher.setUid(uid);
            changeDepartmentByTeacher.setReleaseUid(releaseUid);
            changeDepartmentByTeacher.setNextUid(releaseUid);
            int result = applyService.teacherChangeDepartment(changeDepartmentByTeacher);
            // 更新文件表
            boolean res = updateFiles(uid, tableUid, releaseUid);
            if (res && result == 1) {
                return new Message(200, "提交成功");
            } else if (result == 1) {
                return new Message(210, "提交成功，文件关联异常，请在申请记录中查看详情");
            } else {
                return new Message(400, "提交失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/checkTeacherChangeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkTeacherChangeDepartment(HttpServletRequest request) {
        try {
            if (!CheckToken.checkIsTeacher(request.getHeader("Authorization"))) {
                return new Message(403, "身份验证失败");
            }
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            int i = applyService.checkTeacherChangeDepartment(releaseUid);
            if (i > 0) {
                return new Message(300, "当前有正在审批的部门变更申请");
            } else {
                return new Message(200, "没有正在审批的部门变更申请");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}
