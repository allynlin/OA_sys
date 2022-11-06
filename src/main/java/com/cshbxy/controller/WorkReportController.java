package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.Process;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.*;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.WorkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.cshbxy.Util.DeleteFiles.hideFiles;
import static com.cshbxy.Util.DeleteFiles.updateFiles;

@Controller
@RequestMapping("/apply/workReport")
@CrossOrigin(origins = "*")
public class WorkReportController {

    String processName = "WorkReport";

    @Autowired
    private WorkReportService workReportService;

    @Autowired
    private FileUploadService fileUploadService;

    //判断选择的日期是否是本周
    public static boolean isThisWeek(long time) {
        // 获取现在的日期
        Date now = new Date();
        // 获取 time 的日期
        Date date = new Date(time);
        // 获取现在的日期是星期几
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int nowWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 获取 time 的日期是星期几
        calendar.setTime(date);
        int timeWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 判断是否是同一周
        return nowWeek == timeWeek;
    }

    // 提交工作报告
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message add(HttpServletRequest request) {
        try {
            /// 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            String tableUid = request.getHeader("tableUid");
            // 判断是否提交过工作报告
            WorkReport oldApply = workReportService.checkLastTime(releaseUid);
            if (oldApply != null) {
                // 根据 create_time 判断提交时间是否是上周一到上周日
                String createTime = oldApply.getCreate_time();
                // 将字符串转换为时间戳
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(createTime);
                long time = date.getTime();
                // 判断是不是本周
                if (isThisWeek(time)) {
                    return new Message(400, "本周已提交工作报告");
                }
            }
            WorkReport apply = new WorkReport();
            // 获取审批流程
            String process = Process.getProcess(processName, releaseUid);
            apply.setProcess(process);
            apply.setNextUid(Process.getProcessFirst(process));
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            apply.setUid(uid);
            apply.setReleaseUid(releaseUid);
            int result = workReportService.add(apply);
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

    // 查询上一次提交的工作报告
    @RequestMapping(value = "/checkLastTime", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkLastTime(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 判断是否提交过工作报告
            WorkReport apply = workReportService.checkLastTime(releaseUid);
            if (apply != null) {
                // 根据 create_time 判断提交时间是否是上周一到上周日
                String createTime = apply.getCreate_time();
                // 将字符串转换为时间戳
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(createTime);
                long time = date.getTime();
                // 判断是不是本周
                if (isThisWeek(time)) {
                    return new Message(300, "本周已提交工作报告");
                } else {
                    return new Message(200, "本周未提交工作报告");
                }
            }
            return new Message(200, "本周未提交工作报告");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 查询工作报告提交记录
    @RequestMapping(value = "/findApplyList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findApplyList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String releaseUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 根据提交人查询数据库
            List<WorkReport> list = workReportService.findApplyList(releaseUid);
            // 遍历 list，将 nextUid 通过 findRealeName.findName 转换成真实姓名
            for (WorkReport workReport : list) {
                workReport.setNextUid(findRealeName.findName(workReport.getNextUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询工作报告记录成功", list);
            } else {
                return new Message_body(300, "暂无工作报告记录");
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
            WorkReport apply = workReportService.findWorkReportByUid(uid);
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

    // 删除提交的工作报告
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message delete(HttpServletRequest request, String uid) {
        try {
            String tableUid = request.getHeader("tableUid");
            // 通过接收到的 uid 查询本条申请记录
            WorkReport apply = workReportService.findWorkReportByUid(uid);
            if (apply.getStatus() != 0) {
                return new Message(400, "当前状态不可删除");
            }
            // 判断是否为提交人
            if (!apply.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                return new Message(403, "只能删除自己提交的申请");
            }
            // 删除本条申请记录
            int i = workReportService.delete(uid);
            // 通过 fileUploadService 下的 findUploadFilesByUid 方法，查询本条申请记录的附件
            FileName fileName = new FileName();
            fileName.setRowUid(uid);
            fileName.setTableUid(tableUid);
            List<FileName> list = fileUploadService.findUploadFilesByUid(fileName);
            // 如果有附件，删除附件
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

    // 根据下一级审批人查询工作报告记录
    @RequestMapping(value = "/findWaitList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWaitList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String nextUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 根据下一级审批人查询数据库
            List<WorkReport> list = workReportService.findWaitList(nextUid);
            // 遍历 list，将 releaseUid 通过 findRealeName.findName 转换成真实姓名
            for (WorkReport workReport : list) {
                workReport.setReleaseUid(findRealeName.findName(workReport.getReleaseUid()));
            }
            if (list.size() != 0) {
                return new Message_body(200, "查询工作报告记录成功", list);
            } else {
                return new Message_body(300, "暂无记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 通过工作报告
    @RequestMapping(value = "/resolve", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolve(HttpServletRequest request, String uid) {
        try {
            // 解析 token，获取 uid
            String nowUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 通过 uid 查询本条申请记录
            WorkReport apply = workReportService.findWorkReportByUid(uid);
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
            int i = workReportService.resolve(apply);
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

    // 驳回工作报告
    @RequestMapping(value = "/reject", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message reject(HttpServletRequest request, WorkReport apply) {
        try {
            // 解析 token，获取 uid
            String nowUid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            // 通过 uid 查询本条申请记录
            WorkReport now = workReportService.findWorkReportByUid(apply.getUid());
            // 判断是否为下一级审批人
            if (!now.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 驳回
            int i = workReportService.reject(apply);
            if (i > 0) {
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
            WorkReport apply = workReportService.findWorkReportByUid(uid);
            apply.setReleaseUid(findRealeName.findName(apply.getReleaseUid()));
            return new Message_body(200, "刷新成功", apply);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }
}
