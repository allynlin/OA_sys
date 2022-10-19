package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.Process;
import com.cshbxy.dao.*;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.ProcessService;
import com.cshbxy.service.TeacherService;
import com.cshbxy.service.TravelReimbursementApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/apply")
@CrossOrigin(origins = "*")
public class TravelReimbursementApplyController {

    @Autowired
    private TravelReimbursementApplyService travelReimbursementApplyService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private TeacherService teacherService;

    // 关联上传的文件
    public boolean updateFiles(String uid, String tableUid, String releaseUid) {
        FileName fileName = new FileName();
        fileName.setRowUid(uid);
        fileName.setTableUid(tableUid);
        fileName.setReleaseUid(releaseUid);
        int i = fileUploadService.updateUploadFile(fileName);
        return i > 0;
    }

    public boolean deleteFiles(String fileName) {
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

    public String[] getProcess(TravelReimbursement travelReimbursement) {
        // 通过 uid 查询变更部门申请流程
        Process process = processService.processQueryByUid("4d4b1437-acaa-4eb5-a1ab-9cad78564fed");
        String pro = process.getProcess();
        // 将查询到的数据分离，以 || 分隔
        String[] pros = pro.split("\\|\\|");
        // 修改数组中的 nowDepartment 为当前部门，nextDepartment 为下一部门
        for (int i = 0; i < pros.length; i++) {
            if (pros[i].equals("nowDepartment")) {
                pros[i] = teacherService.findDepartmentUid(travelReimbursement.getReleaseUid());
            }
        }
        return pros;
    }

    // 查找下一级审批人
    public String findNextProcessPerson(TravelReimbursement travelReimbursement) {
        try {
            String[] pros = getProcess(travelReimbursement);
            if (travelReimbursement.getNextUid() == null) {
                return pros[0];
            }
            // 遍历数组，根据 NowUid 寻找下一级审批人
            for (int i = 0; i < pros.length; i++) {
                if (pros[i].equals(travelReimbursement.getNextUid())) {
                    return pros[i + 1];
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 提交差旅报销申请
    @RequestMapping(value = "/addTravelReimbursement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addTravelReimbursement(HttpServletRequest request, TravelReimbursement travelReimbursement) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            travelReimbursement.setReleaseUid(releaseUid);
            String tableUid = request.getHeader("tableUid");
            // 查询流程
            String nextProcessPerson = findNextProcessPerson(travelReimbursement);
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            travelReimbursement.setUid(uid);
            travelReimbursement.setNextUid(nextProcessPerson);
            int result = travelReimbursementApplyService.addTravelReimbursement(travelReimbursement);
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

    // 查询差旅报销申请记录
    @RequestMapping(value = "/findTravelReimbursementApplyList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findTravelReimbursementApplyList(HttpServletRequest request, TravelReimbursement travelReimbursement) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 根据提交人查询数据库
            travelReimbursement.setReleaseUid(releaseUid);
            List<TravelReimbursement> list = travelReimbursementApplyService.findTravelReimbursementApplyList(travelReimbursement);
            if (list.size() != 0) {
                return new Message_body(200, "查询差旅报销申请记录成功", list);
            } else {
                return new Message_body(300, "暂无差旅报销申请记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 查询审批流程
    @RequestMapping(value = "/findTravelProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findTravelProcess(HttpServletRequest request, String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            TravelReimbursement travelReimbursement = travelReimbursementApplyService.findTravelProcess(uid);
            // 查询审批流程
            String[] pros = getProcess(travelReimbursement);
            // 将 list 中的每个 uid 都在 findName 中查询，返回真是姓名
            List<String> list = new ArrayList<>();
            for (String pro : pros) {
                String name = findRealeName.findName(pro);
                list.add(name);
            }
            return new Message_body(200, "查询审批流程成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 修改提交的差旅报销申请
    @RequestMapping(value = "/updateTravelReimbursementApply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateTravelReimbursementApply(HttpServletRequest request, TravelReimbursement travelReimbursement) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            String tableUid = request.getHeader("tableUid");
            travelReimbursement.setStatus(0);
            travelReimbursement.setCount(0);
            travelReimbursement.setNextUid(null);
            travelReimbursement.setReleaseUid(releaseUid);
            // 查询流程
            String nextProcessPerson = findNextProcessPerson(travelReimbursement);
            travelReimbursement.setNextUid(nextProcessPerson);
            int result = travelReimbursementApplyService.updateTravelReimbursementApply(travelReimbursement);
            // 更新文件表
            boolean res = updateFiles(travelReimbursement.getUid(), tableUid, releaseUid);
            if (res && result == 1) {
                return new Message(200, "修改成功");
            } else if (result == 1) {
                return new Message(210, "修改成功，如您修改了附件，可能存在异常，请在申请记录中查看详情，如您未修改附件，可忽略此提示");
            } else {
                return new Message(400, "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 删除提交的差旅报销申请
    @RequestMapping(value = "/deleteTravelReimbursementApply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message deleteTravelReimbursementApply(HttpServletRequest request, String uid) {
        try {
            String tableUid = request.getHeader("tableUid");
            // 通过接收到的 uid 查询本条申请记录
            TravelReimbursement travelReimbursement = travelReimbursementApplyService.findTravelProcess(uid);
            // 判断是否为提交人
            if (travelReimbursement.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                // 删除本条申请记录
                int i = travelReimbursementApplyService.deleteTravelReimbursementApply(uid);
                // 通过 fileUploadService 下的 findUploadFilesByUid 方法，查询本条申请记录的附件
                FileName fileName = new FileName();
                fileName.setRowUid(uid);
                fileName.setTableUid(tableUid);
                List<FileName> list = fileUploadService.findUploadFilesByUid(fileName);
                // 如果有附件，删除附件
                boolean result = true;
                if (list.size() != 0) {
                    for (FileName file : list) {
                        result = deleteFiles(file.getFileName());
                    }
                }
                if (i > 0 && result) {
                    return new Message(200, "删除成功");
                } else if (i > 0) {
                    return new Message(200, "删除成功，附件删除失败");
                } else {
                    return new Message(300, "删除失败");
                }
            } else {
                return new Message(403, "只能删除自己提交的申请");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}
