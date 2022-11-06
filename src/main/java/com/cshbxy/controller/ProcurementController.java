package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.Process;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Message_body;
import com.cshbxy.dao.Procurement;
import com.cshbxy.service.ProcurementSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/apply/procurement")
@CrossOrigin(origins = "*")
public class ProcurementController {

    String processName = "Procurement";

    @Autowired
    private ProcurementSerivce procurementSerivce;

    // 提交采购申请
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message add(HttpServletRequest request, Procurement apply) {
        try {
            // 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 获取审批流程
            String process = Process.getProcess(processName, releaseUid);
            apply.setProcess(process);
            apply.setNextUid(Process.getProcessFirst(process));
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            apply.setUid(uid);
            apply.setReleaseUid(releaseUid);
            int result = procurementSerivce.add(apply);
            if (result == 1) {
                return new Message(200, "提交成功");
            } else {
                return new Message(400, "提交失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 查询采购记录
    @RequestMapping(value = "/findApplyList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findApplyList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 根据提交人查询数据库
            List<Procurement> list = procurementSerivce.findApplyList(releaseUid);
            // 遍历 list，将 nextUid 通过 findRealeName.findName 转换成真实姓名
            for (Procurement procurement : list) {
                procurement.setNextUid(findRealeName.findName(procurement.getNextUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询采购记录成功", list);
            } else {
                return new Message_body(300, "暂无采购记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 查询采购流程
    @RequestMapping(value = "/findProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findProcess(String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Procurement apply = procurementSerivce.findProcurementByUid(uid);
            // 查询审批流程，将查询到的流程按照 || 分割
            String[] processList = apply.getProcess().split("\\|\\|");
            List<String> list = new ArrayList<>();
            for (String pro : processList) {
                String name = findRealeName.findName(pro);
                list.add(name);
            }
            return new Message_body(200, "查询采购流程成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 删除提交的采购申请
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message delete(HttpServletRequest request, String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Procurement apply = procurementSerivce.findProcurementByUid(uid);
            // 判断是否为提交人
            if (!apply.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                return new Message(403, "只能删除自己提交的申请");
            }
            // 删除本条申请记录
            int i = procurementSerivce.delete(uid);
            if (i == 1) {
                return new Message(200, "删除成功");
            } else {
                return new Message(400, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 更新采购申请
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message update(HttpServletRequest request, Procurement newApply) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Procurement apply = procurementSerivce.findProcurementByUid(newApply.getUid());
            if (apply.getStatus() != 0) {
                return new Message(400, "当前状态不可修改");
            }
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            if (!Objects.equals(apply.getReleaseUid(), releaseUid)) {
                return new Message(403, "只能修改自己提交的申请");
            }
            apply.setCount(0);
            // 获取审批流程
            String process = Process.getProcess(processName, releaseUid);
            apply.setProcess(process);
            apply.setNextUid(Process.getProcessFirst(process));
            apply.setPrice(newApply.getPrice());
            apply.setReason(newApply.getReason());
            apply.setItems(newApply.getItems());
            // 修改数据库
            int i = procurementSerivce.update(apply);
            if (i == 1) {
                return new Message(200, "修改成功");
            } else {
                return new Message(400, "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 根据下一级审批人查询采购申请
    @RequestMapping(value = "/findWaitList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWaitList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String nextUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 根据下一级审批人查询数据库
            List<Procurement> list = procurementSerivce.findWaitList(nextUid);
            // 遍历 list，将 releaseUid 通过 findRealeName.findName 转换成真实姓名
            for (Procurement procurement : list) {
                procurement.setReleaseUid(findRealeName.findName(procurement.getReleaseUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询采购申请成功", list);
            } else {
                return new Message_body(300, "暂无采购申请");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 通过采购申请
    @RequestMapping(value = "/resolve", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolve(HttpServletRequest request, String uid) {
        try {
            // 解析 token，获取 uid
            String nowUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 通过接收到的 uid 查询本条申请记录
            Procurement apply = procurementSerivce.findProcurementByUid(uid);
            // 判断是否为下一级审批人
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "您不是当前审批人，无法审批");
            }
            // 查询审批流程
            String[] pros = apply.getProcess().split("\\|\\|");
            // 查询 nowUid 是不是 props 的最后一个
            if (pros[pros.length - 1].equals(nowUid)) {
                // 是最后一个，审批通过
                apply.setStatus(1);
                apply.setNextUid(null);
            } else {
                /// 在 pros 中查找下一个审批人
                for (int i = 0; i < pros.length; i++) {
                    if (pros[i].equals(nowUid)) {
                        apply.setNextUid(pros[i + 1]);
                        break;
                    }
                }
                apply.setCount(apply.getCount() + 1);
            }
            int i = procurementSerivce.resolve(apply);
            if (i == 1) {
                return new Message(200, "审批成功");
            } else {
                return new Message(400, "审批失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 驳回采购申请
    @RequestMapping(value = "/reject", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message reject(HttpServletRequest request, Procurement apply) {
        try {
            // 解析 token，获取 uid
            String nowUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 通过接收到的 uid 查询本条申请记录
            Procurement now = procurementSerivce.findProcurementByUid(apply.getUid());
            // 判断是否为下一级审批人
            if (!now.getNextUid().equals(nowUid)) {
                return new Message(403, "您不是当前审批人，无法审批");
            }
            int i = procurementSerivce.reject(apply);
            if (i == 1) {
                return new Message(200, "驳回成功");
            } else {
                return new Message(400, "驳回失败");
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
            Procurement apply = procurementSerivce.findProcurementByUid(uid);
            apply.setReleaseUid(findRealeName.findName(apply.getReleaseUid()));
            return new Message_body(200, "刷新成功", apply);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }
}
