package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.*;
import com.cshbxy.service.ChangeDepartmentByTeacherApplyService;
import com.cshbxy.service.FileUploadService;
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

import static com.cshbxy.Util.DeleteFiles.deleteFiles;
import static com.cshbxy.Util.DeleteFiles.updateFiles;
import static com.cshbxy.Util.Process.findNextProcessPerson;
import static com.cshbxy.Util.Process.getProcess;

@Controller
@RequestMapping("/apply")
@CrossOrigin(origins = "*")
public class ChangeDepartmentByTeacherApplyController {

    String processUid = "ab0c4b48-0651-4f22-ba23-4587ae502e89";

    @Autowired
    private ChangeDepartmentByTeacherApplyService changeDepartmentByTeacherApplyService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private FileUploadService fileUploadService;

    // 提交部门变更申请
    @RequestMapping(value = "/teacherChangeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message teacherChangeDepartment(HttpServletRequest request, ChangeDepartmentByTeacher changeDepartmentByTeacher) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            changeDepartmentByTeacher.setReleaseUid(releaseUid);
            String tableUid = request.getHeader("tableUid");
            // 判断是否提交过部门变更申请
            int i = changeDepartmentByTeacherApplyService.checkTeacherChangeDepartment(releaseUid);
            if (i > 0) {
                return new Message(401, "您已提交过部门变更申请，请勿重复提交");
            }
            // 查询流程
            String nextProcessPerson = findNextProcessPerson(processUid, releaseUid, null, changeDepartmentByTeacher.getDepartmentUid());
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            changeDepartmentByTeacher.setUid(uid);
            changeDepartmentByTeacher.setNextUid(nextProcessPerson);
            int result = changeDepartmentByTeacherApplyService.teacherChangeDepartment(changeDepartmentByTeacher);
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

    // 通过申请人查询正在审批的部门变更申请
    @RequestMapping(value = "/checkTeacherChangeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkTeacherChangeDepartment(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            int i = changeDepartmentByTeacherApplyService.checkTeacherChangeDepartment(releaseUid);
            if (i > 0) {
                return new Message(300, "当前有正在审批的部门变更记录");
            } else {
                return new Message(200, "没有正在审批的部门变更记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 查询部门变更申请记录
    @RequestMapping(value = "/findTeacherChangeDepartmentList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findTeacherChangeDepartmentList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 根据提交人查询数据库
            List<ChangeDepartmentByTeacher> list = changeDepartmentByTeacherApplyService.findChangeDepartmentByTeacherList(releaseUid);
            // 遍历 list，将 departmentUid 通过 findRealeName.findName 转换成真实姓名
            for (ChangeDepartmentByTeacher changeDepartmentByTeacher : list) {
                changeDepartmentByTeacher.setDepartmentUid(findRealeName.findName(changeDepartmentByTeacher.getDepartmentUid()));
            }
            // 遍历 list，将 nextUid 通过 findRealeName.findName 转换成真实姓名
            for (ChangeDepartmentByTeacher changeDepartmentByTeacher : list) {
                changeDepartmentByTeacher.setNextUid(findRealeName.findName(changeDepartmentByTeacher.getNextUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询部门变更记录成功", list);
            } else {
                return new Message_body(300, "暂无部门变更记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 查询审批流程
    @RequestMapping(value = "/findChangeDepartmentByTeacherProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findChangeDepartmentByTeacherProcess(HttpServletRequest request, String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            ChangeDepartmentByTeacher changeDepartmentByTeacher = changeDepartmentByTeacherApplyService.findChangeDepartmentByTeacher(uid);
            // 查询审批流程
            String[] pros = getProcess(processUid, changeDepartmentByTeacher.getReleaseUid(), changeDepartmentByTeacher.getDepartmentUid());
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

    // 删除提交的部门变更申请
    @RequestMapping(value = "/deleteChangeDepartmentByTeacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message deleteChangeDepartmentByTeacher(HttpServletRequest request, String uid) {
        try {
            String tableUid = request.getHeader("tableUid");
            // 通过接收到的 uid 查询本条申请记录
            ChangeDepartmentByTeacher changeDepartmentByTeacher = changeDepartmentByTeacherApplyService.findChangeDepartmentByTeacher(uid);
            if (changeDepartmentByTeacher.getStatus() != 0)
                return new Message(400, "当前状态不可删除");
            // 判断是否为提交人
            if (changeDepartmentByTeacher.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                // 删除本条申请记录
                int i = changeDepartmentByTeacherApplyService.deleteByUid(uid);
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

    // 根据下一级审批人查询部门变更申请
    @RequestMapping(value = "/findWaitList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWaitList(HttpServletRequest request) {
        try {
            // 获取 token
            String token = request.getHeader("Authorization");
            // 通过 token 获取用户 uid
            String nextUid = JwtUtil.getUserUid(token);
            // 根据 uid 查询数据库
            List<ChangeDepartmentByTeacher> list = changeDepartmentByTeacherApplyService.findWaitList(nextUid);
            // 遍历 list，将 departmentUid 通过 findRealeName.findName 转换成真实姓名
            for (ChangeDepartmentByTeacher changeDepartmentByTeacher : list) {
                changeDepartmentByTeacher.setDepartmentUid(findRealeName.findName(changeDepartmentByTeacher.getDepartmentUid()));
            }
            // 遍历 list，将 releaseUid 通过 findRealeName.findName 转换成真实姓名
            for (ChangeDepartmentByTeacher changeDepartmentByTeacher : list) {
                changeDepartmentByTeacher.setReleaseUid(findRealeName.findName(changeDepartmentByTeacher.getReleaseUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询部门变更申请成功", list);
            } else {
                return new Message_body(300, "暂无部门变更申请");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 通过部门变更申请
    @RequestMapping(value = "/resolveApply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolveApply(HttpServletRequest request, String uid) {
        try {
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 通过接收到的 uid 查询本条申请记录
            ChangeDepartmentByTeacher apply = changeDepartmentByTeacherApplyService.findChangeDepartmentByTeacher(uid);
            // 如果不是下一级审人，不能审批
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "您不是当前审批人，无法审批");
            }
            // 查询审批流程
            String[] pros = getProcess(processUid, apply.getReleaseUid(), apply.getDepartmentUid());
            // 查询 nowUid 是不是 props 的最后一个
            if (pros[pros.length - 1].equals(nowUid)) {
                // 如果审批通过，修改本条申请记录的状态为 1
                apply.setStatus(1);
                apply.setNextUid(null);
                int i = changeDepartmentByTeacherApplyService.resolveApply(apply);
                // 修改教师表中的部门
                Teacher teacher = new Teacher();
                teacher.setUid(apply.getReleaseUid());
                teacher.setDepartmentUid(apply.getDepartmentUid());
                int j = teacherService.updateDepartment(teacher);
                if (i > 0 && j > 0) {
                    return new Message(200, "审批通过");
                } else if (i > 0) {
                    return new Message(200, "审批通过，修改部门失败");
                } else {
                    return new Message(400, "审批失败");
                }
            } else {
                String nextProcessPerson = findNextProcessPerson(processUid, apply.getReleaseUid(), nowUid, apply.getDepartmentUid());
                apply.setNextUid(nextProcessPerson);
                apply.setCount(apply.getCount() + 1);
                // 如果不是最后一个审批人，修改本条申请记录的下一级审批人为下一个审批人
                int i = changeDepartmentByTeacherApplyService.resolveApply(apply);
                if (i > 0) {
                    return new Message(200, "审批成功");
                } else {
                    return new Message(300, "审批失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 驳回部门变更申请
    @RequestMapping(value = "/rejectApply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message rejectApply(HttpServletRequest request, ChangeDepartmentByTeacher changeDepartmentByTeacher) {
        try {
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 通过接收到的 uid 查询本条申请记录
            ChangeDepartmentByTeacher apply = changeDepartmentByTeacherApplyService.findChangeDepartmentByTeacher(changeDepartmentByTeacher.getUid());
            // 如果不是下一级审人，不能审批
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "您不是当前审批人，无法审批");
            }
            apply.setReject_reason(changeDepartmentByTeacher.getReject_reason());
            int i = changeDepartmentByTeacherApplyService.rejectApply(apply);
            if (i > 0) {
                return new Message(200, "驳回成功");
            }
            return new Message(400, "驳回失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}