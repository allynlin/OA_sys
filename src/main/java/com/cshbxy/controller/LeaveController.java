package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.Leave;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Message_body;
import com.cshbxy.dao.Process;
import com.cshbxy.service.LeaveSerivce;
import com.cshbxy.service.ProcessService;
import com.cshbxy.service.TeacherService;
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

@Controller
@RequestMapping("/leave")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveSerivce leaveSerivce;

    @Autowired
    private ProcessService processService;

    @Autowired
    private TeacherService teacherService;

    public String[] getProcess(Leave leave) {
        // 通过 uid 查询变更部门申请流程
        Process process = processService.processQueryByUid("64c0e0cc-8c3b-40de-aff3-3d293b6ac868");
        String pro = process.getProcess();
        // 将查询到的数据分离，以 || 分隔
        String[] pros = pro.split("\\|\\|");
        // 修改数组中的 nowDepartment 为当前部门
        for (int i = 0; i < pros.length; i++) {
            if (pros[i].equals("nowDepartment")) {
                pros[i] = teacherService.findDepartmentUid(leave.getReleaseUid());
            }
        }
        return pros;
    }

    // 查找下一级审批人
    public String findNextProcessPerson(Leave leave) {
        try {
            String[] pros = getProcess(leave);
            if (leave.getNextUid() == null) {
                return pros[0];
            }
            // 遍历数组，根据 NowUid 寻找下一级审批人
            for (int i = 0; i < pros.length; i++) {
                if (pros[i].equals(leave.getNextUid())) {
                    return pros[i + 1];
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 提交请假申请
    @RequestMapping(value = "/addLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addLeave(HttpServletRequest request, Leave leave) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 查询流程
            leave.setReleaseUid(releaseUid);
            String nextProcessPerson = findNextProcessPerson(leave);
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            leave.setUid(uid);
            leave.setNextUid(nextProcessPerson);
            int result = leaveSerivce.addLeave(leave);
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

    // 查询请假记录
    @RequestMapping(value = "/findLeaveList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findLeaveList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 根据提交人查询数据库
            List<Leave> list = leaveSerivce.findLeaveList(releaseUid);
            if (list.size() != 0) {
                return new Message_body(200, "查询请假记录成功", list);
            } else {
                return new Message_body(300, "暂无请假记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 查询审批流程
    @RequestMapping(value = "/findLeaveProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findLeaveProcess(String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Leave leave = leaveSerivce.findLeaveByUid(uid);
            // 查询审批流程
            String[] pros = getProcess(leave);
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

    // 删除提交的请假申请
    @RequestMapping(value = "/deleteLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message deleteLeave(HttpServletRequest request, String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            Leave leave = leaveSerivce.findLeaveByUid(uid);
            // 判断是否为提交人
            if (leave.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                // 删除本条申请记录
                int i = leaveSerivce.deleteLeave(uid);
                if (i == 1) {
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

    // 检查上一次提交的请假申请
    @RequestMapping(value = "/checkLastTimeLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body checkLastTimeLeave(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 查询数据库
            Leave leave = leaveSerivce.checkLastTimeLeave(releaseUid);
            if (leave != null) {
                return new Message_body(200, "查询成功", leave);
            } else {
                return new Message_body(300, "暂无记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 更新请假申请
    @RequestMapping(value = "/updateLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateLeave(HttpServletRequest request, Leave leave) {
        try {
            leave.setCount(0);
            leave.setStatus(0);
            leave.setReleaseUid(JwtUtil.getUserUid(request.getHeader("Authorization")));
            String nextProcessPerson = findNextProcessPerson(leave);
            leave.setNextUid(nextProcessPerson);
            // 修改数据库
            int i = leaveSerivce.updateLeave(leave);
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
}
