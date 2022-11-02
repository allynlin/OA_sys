package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
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
import java.util.UUID;

import static com.cshbxy.Util.Process.findNextProcessPerson;
import static com.cshbxy.Util.Process.getProcess;

@Controller
@RequestMapping("/procurement")
@CrossOrigin(origins = "*")
public class ProcurementController {

    String processUid = "ebd453af-2773-449b-8ece-b09b23e99d76";

    @Autowired
    private ProcurementSerivce procurementSerivce;

    // 提交采购申请
    @RequestMapping(value = "/addProcurement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addProcurement(HttpServletRequest request, Procurement procurement) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 查询流程
            String nextProcessPerson = findNextProcessPerson(processUid, releaseUid, procurement.getNextUid(), null);
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            procurement.setUid(uid);
            procurement.setReleaseUid(releaseUid);
            procurement.setNextUid(nextProcessPerson);
            int result = procurementSerivce.addProcurement(procurement);
            if (result == 1) {
                return new Message(200, "提交成功采购申请成功");
            } else {
                return new Message(400, "提交采购申请失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 查询采购记录
    @RequestMapping(value = "/findProcurementList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findProcurementList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 根据提交人查询数据库
            List<Procurement> list = procurementSerivce.findProcurementList(releaseUid);
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
    @RequestMapping(value = "/findProcurementProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findProcurementProcess(String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Procurement procurement = procurementSerivce.findProcurementByUid(uid);
            // 查询审批流程
            String[] pros = getProcess(processUid, procurement.getReleaseUid(), null);
            // 将 list 中的每个 uid 都在 findName 中查询，返回真是姓名
            List<String> list = new ArrayList<>();
            for (String pro : pros) {
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
    @RequestMapping(value = "/deleteProcurement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message deleteProcurement(HttpServletRequest request, String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Procurement procurement = procurementSerivce.findProcurementByUid(uid);
            // 判断是否为提交人
            if (procurement.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                // 删除本条申请记录
                int result = procurementSerivce.deleteProcurement(uid);
                if (result == 1) {
                    return new Message(200, "删除成功");
                } else {
                    return new Message(400, "删除失败");
                }
            } else {
                return new Message(403, "只能删除自己提交的申请");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 更新采购申请
    @RequestMapping(value = "/updateProcurement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateProcurement(HttpServletRequest request, Procurement procurement) {
        try {
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            procurement.setCount(0);
            procurement.setStatus(0);
            procurement.setReleaseUid(releaseUid);
            String nextProcessPerson = findNextProcessPerson(processUid, procurement.getReleaseUid(), null, null);
            procurement.setNextUid(nextProcessPerson);
            // 修改数据库
            int result = procurementSerivce.updateProcurement(procurement);
            if (result == 1) {
                return new Message(200, "修改成功");
            } else {
                return new Message(400, "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}
