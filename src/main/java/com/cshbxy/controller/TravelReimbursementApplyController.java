package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.FileName;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Message_body;
import com.cshbxy.dao.TravelReimbursement;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.TeacherService;
import com.cshbxy.service.TravelReimbursementApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.cshbxy.Util.DeleteFiles.deleteFiles;
import static com.cshbxy.Util.DeleteFiles.updateFiles;
import static com.cshbxy.Util.Process.findNextProcessPerson;
import static com.cshbxy.Util.Process.getProcess;

@Controller
@RequestMapping("/apply")
@CrossOrigin(origins = "*")
public class TravelReimbursementApplyController {

    String processUid = "4d4b1437-acaa-4eb5-a1ab-9cad78564fed";

    @Autowired
    private TravelReimbursementApplyService travelReimbursementApplyService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private TeacherService teacherService;

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
            String nextProcessPerson = findNextProcessPerson(processUid, releaseUid, travelReimbursement.getNextUid(), null);
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
            String[] pros = getProcess(processUid, travelReimbursement.getReleaseUid(), null);
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
            travelReimbursement.setReleaseUid(releaseUid);
            // 查询流程
            String nextProcessPerson = findNextProcessPerson(processUid, releaseUid, null, null);
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

    // 根据下一级审批人查询差旅报销记录
    @RequestMapping(value = "/findTravelReimbursementListByNextUid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findTravelReimbursementListByNextUid(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nextUid = JwtUtil.getUserUid(token);
            // 根据下一级审批人查询数据库
            List<TravelReimbursement> list = travelReimbursementApplyService.findTravelReimbursementWaitList(nextUid);
            // 遍历 list，将 releaseUid 通过 findRealeName.findName 转换成真实姓名
            for (TravelReimbursement travelReimbursement : list) {
                travelReimbursement.setReleaseUid(findRealeName.findName(travelReimbursement.getReleaseUid()));
            }
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

    // 通过差旅报销申请
    @RequestMapping(value = "/resolveTravelReimbursement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolveTravelReimbursement(HttpServletRequest request, String uid) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 查询数据库
            TravelReimbursement travelReimbursement = travelReimbursementApplyService.findTravelProcess(uid);
            // 判断是否为下一级审批人
            if (!travelReimbursement.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 查询审批流程
            String[] props = getProcess(processUid, travelReimbursement.getReleaseUid(), teacherService.findDepartmentUid(travelReimbursement.getReleaseUid()));
            // 查询 nowUid 是不是 props 的最后一个
            if (nowUid.equals(props[props.length - 1])) {
                // 是最后一个，审批通过
                travelReimbursement.setStatus(1);
                travelReimbursement.setNextUid(null);
                int i = travelReimbursementApplyService.resolveTravelReimbursement(travelReimbursement);
                if (i > 0) {
                    return new Message(200, "审批通过");
                } else {
                    return new Message(300, "审批失败");
                }
            } else {
                // 不是最后一个，继续审批
                String nextProcessPerson = findNextProcessPerson(processUid, travelReimbursement.getReleaseUid(), nowUid, null);
                travelReimbursement.setNextUid(nextProcessPerson);
                travelReimbursement.setCount(travelReimbursement.getCount() + 1);
                int i = travelReimbursementApplyService.resolveTravelReimbursement(travelReimbursement);
                if (i > 0) {
                    return new Message(200, "审批通过");
                } else {
                    return new Message(300, "审批失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 驳回差旅报销申请
    @RequestMapping(value = "/rejectTravelReimbursement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message rejectTravelReimbursement(HttpServletRequest request, TravelReimbursement travelReimbursement) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 查询数据库
            TravelReimbursement apply = travelReimbursementApplyService.findTravelProcess(travelReimbursement.getUid());
            // 判断是否为下一级审批人
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 修改数据库
            int i = travelReimbursementApplyService.rejectTravelReimbursement(travelReimbursement);
            if (i > 0) {
                return new Message(200, "驳回成功");
            } else {
                return new Message(300, "驳回失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}
