package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.Process;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.*;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.TravelService;
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

import static com.cshbxy.Util.DeleteFiles.hideFiles;
import static com.cshbxy.Util.DeleteFiles.updateFiles;

@Controller
@RequestMapping("/apply/travel")
@CrossOrigin(origins = "*")
public class TravelController {

    String processName = "Travel";

    @Autowired
    private TravelService travelService;

    @Autowired
    private FileUploadService fileUploadService;

    // 提交差旅报销申请
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addTravelReimbursement(HttpServletRequest request, Travel apply) {
        try {
            /// 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            apply.setReleaseUid(releaseUid);
            String tableUid = request.getHeader("tableUid");
            // 获取审批流程
            String process = Process.getProcess(processName, releaseUid);
            apply.setProcess(process);
            apply.setNextUid(Process.getProcessFirst(process));
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            apply.setUid(uid);
            int result = travelService.add(apply);
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

    // 查询提交过的差旅报销申请记录
    @RequestMapping(value = "/findApplyList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findApplyList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 根据提交人查询数据库
            List<Travel> list = travelService.findApplyList(releaseUid);
            // 遍历 list，将 nextUid 通过 findRealeName.findName 转换成真实姓名
            for (Travel travel : list) {
                travel.setNextUid(findRealeName.findName(travel.getNextUid()));
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

    // 查询审批流程
    @RequestMapping(value = "/findProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findProcess(String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Travel apply = travelService.findTravelByUid(uid);
            // 查询审批流程，将查询到的流程按照 || 分割
            String[] processList = apply.getProcess().split("\\|\\|");
            // 将 list 中的每个 uid 都在 findName 中查询，返回真是姓名
            List<String> list = new ArrayList<>();
            for (String pro : processList) {
                String name = findRealeName.findName(pro);
                list.add(name);
            }
            return new Message_body(200, "查询审批流程成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 删除提交的差旅报销申请
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message delete(HttpServletRequest request, String uid) {
        try {
            String tableUid = request.getHeader("tableUid");
            // 通过接收到的 uid 查询本条申请记录
            Travel apply = travelService.findTravelByUid(uid);
            if (apply.getStatus() != 0)
                return new Message(400, "当前状态不可删除");
            // 判断是否为提交人
            if (!apply.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                return new Message(403, "只能删除自己提交的申请");
            }
            // 删除本条申请记录
            int i = travelService.delete(uid);
            // 通过 fileUploadService 下的 findUploadFilesByUid 方法，查询本条申请记录的附件
            FileName fileName = new FileName();
            fileName.setRowUid(uid);
            fileName.setTableUid(tableUid);
            List<FileName> list = fileUploadService.findUploadFilesByUid(fileName);
            // 如果有附件，移动附件
            boolean result = true;
            if (list.size() != 0) {
                for (FileName file : list) {
                    result = hideFiles(file.getFileName());
                }
            }
            if (i > 0 && result) {
                return new Message(200, "删除成功");
            } else if (i > 0) {
                return new Message(200, "删除成功，附件删除失败");
            } else {
                return new Message(300, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 根据下一级审批人查询差旅报销记录
    @RequestMapping(value = "/findWaitList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWaitList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String nextUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 根据 uid 查询数据库
            List<Travel> list = travelService.findWaitList(nextUid);
            // 遍历 list，将 releaseUid 通过 findRealeName.findName 转换成真实姓名
            for (Travel travel : list) {
                travel.setReleaseUid(findRealeName.findName(travel.getReleaseUid()));
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
    @RequestMapping(value = "/resolve", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolve(HttpServletRequest request, String uid) {
        try {
            // 解析 token，获取 uid
            String nowUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 查询数据库
            Travel apply = travelService.findTravelByUid(uid);
            // 判断是否为下一级审批人
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 查询审批流程
            String[] pros = apply.getProcess().split("\\|\\|");
            // 查询 nowUid 是不是 props 的最后一个
            if (pros[pros.length - 1].equals(nowUid)) {
                // 是最后一个，审批通过
                apply.setStatus(1);
                apply.setNextUid(null);
            } else {
                // 在 pros 中查找下一个审批人
                for (int i = 0; i < pros.length; i++) {
                    if (pros[i].equals(nowUid)) {
                        apply.setNextUid(pros[i + 1]);
                        break;
                    }
                }
                apply.setCount(apply.getCount() + 1);
            }
            int i = travelService.resolve(apply);
            if (i > 0) {
                return new Message(200, "审批通过");
            } else {
                return new Message(400, "审批失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 驳回差旅报销申请
    @RequestMapping(value = "/reject", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message reject(HttpServletRequest request, Travel apply) {
        try {
            // 解析 token，获取 uid
            String nowUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 查询数据库
            Travel now = travelService.findTravelByUid(apply.getUid());
            // 如果不是下一级审人，不能审批
            if (!now.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 修改数据库
            int i = travelService.reject(apply);
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

    // 刷新当前申请
    @RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body refresh(String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Travel apply = travelService.findTravelByUid(uid);
            return new Message_body(200, "刷新成功", apply);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }
}
