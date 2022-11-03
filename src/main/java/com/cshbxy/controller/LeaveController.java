package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.Leave;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Message_body;
import com.cshbxy.service.LeaveSerivce;
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

import static com.cshbxy.Util.Process.findNextProcessPerson;
import static com.cshbxy.Util.Process.getProcess;

@Controller
@RequestMapping("/leave")
@CrossOrigin(origins = "*")
public class LeaveController {

    String processUid = "64c0e0cc-8c3b-40de-aff3-3d293b6ac868";

    @Autowired
    private LeaveSerivce leaveSerivce;

    @Autowired
    private TeacherService teacherService;

    // 提交请假申请
    @RequestMapping(value = "/addLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addLeave(HttpServletRequest request, Leave leave) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 查询流程
            String nextProcessPerson = findNextProcessPerson(processUid, releaseUid, leave.getNextUid(), null);
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            leave.setUid(uid);
            leave.setReleaseUid(releaseUid);
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
            String[] pros = getProcess(processUid, leave.getReleaseUid(), null);
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
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            leave.setCount(0);
            leave.setStatus(0);
            leave.setReleaseUid(releaseUid);
            String nextProcessPerson = findNextProcessPerson(processUid, leave.getReleaseUid(), null, null);
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

    // 根据下一级审批人查询请假记录
    @RequestMapping(value = "/findLeaveListByNextUid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findLeaveListByNextUid(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nextUid = JwtUtil.getUserUid(token);
            // 根据下一级审批人查询数据库
            List<Leave> list = leaveSerivce.findLeaveWaitList(nextUid);
            // 遍历 list，将 releaseUid 通过 findRealeName.findName 转换成真实姓名
            for (Leave leave : list) {
                leave.setReleaseUid(findRealeName.findName(leave.getReleaseUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询请假申请记录成功", list);
            } else {
                return new Message_body(300, "暂无请假申请记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 通过请假申请
    @RequestMapping(value = "/resolveLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolveLeave(HttpServletRequest request, String uid) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 查询数据库
            Leave leave = leaveSerivce.findLeaveByUid(uid);
            // 判断是否为下一步审批人
            if (!leave.getNextUid().equals(nowUid)) {
                return new Message(403, "您不是当前审批人，无法审批");
            }
            // 查询审批流程
            String[] props = getProcess(processUid, leave.getReleaseUid(), teacherService.findDepartmentUid(leave.getReleaseUid()));
            // 查询 nowUid 是不是 props 的最后一个
            if (nowUid.equals(props[props.length - 1])) {
                // 是最后一个，审批通过
                leave.setStatus(1);
                leave.setNextUid(null);
                // 修改数据库
                int i = leaveSerivce.resolveLeave(leave);
                if (i == 1) {
                    return new Message(200, "审批通过");
                } else {
                    return new Message(400, "审批失败");
                }
            } else {
                // 不是最后一个，继续审批
                String nextProcessPerson = findNextProcessPerson(processUid, leave.getReleaseUid(), nowUid, null);
                leave.setNextUid(nextProcessPerson);
                leave.setCount(leave.getCount() + 1);
                // 修改数据库
                int i = leaveSerivce.updateLeave(leave);
                if (i == 1) {
                    return new Message(200, "审批通过");
                } else {
                    return new Message(400, "审批失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 驳回请假申请
    @RequestMapping(value = "/rejectLeave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message rejectLeave(HttpServletRequest request, Leave leave) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 查询数据库
            Leave apply = leaveSerivce.findLeaveByUid(leave.getUid());
            // 判断是否为下一步审批人
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "您不是当前审批人，无法审批");
            }
            // 修改数据库
            int i = leaveSerivce.rejectLeave(leave);
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
}
