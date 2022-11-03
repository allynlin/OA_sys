package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.FileName;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Message_body;
import com.cshbxy.dao.WorkReport;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.TeacherService;
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

import static com.cshbxy.Util.DeleteFiles.deleteFiles;
import static com.cshbxy.Util.DeleteFiles.updateFiles;
import static com.cshbxy.Util.Process.findNextProcessPerson;
import static com.cshbxy.Util.Process.getProcess;

@Controller
@RequestMapping("/workReport")
@CrossOrigin(origins = "*")
public class WorkReportController {

    String processUid = "5c4a2ce8-6c37-402b-bff3-998da0cc3376";

    @Autowired
    private WorkReportService workReportService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private TeacherService teacherService;

    //判断选择的日期是否是今天
    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }


    //判断选择的日期是否是本月
    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }

    public static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        return param.equals(now);
    }

    // 提交工作报告
    @RequestMapping(value = "/addWorkReport", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addWorkReport(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            String tableUid = request.getHeader("tableUid");
            // 判断是否提交过工作报告
            WorkReport workReport = workReportService.checkLastWeekWorkReport(releaseUid);
            if (workReport != null) {
                // 根据 create_time 判断提交时间是否是上周一到上周日
                String createTime = workReport.getCreate_time();
                // 将字符串转换为时间戳
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(createTime);
                long time = date.getTime();
                // 判断是不是本周
                if (isThisWeek(time)) {
                    return new Message(400, "本周已提交工作报告");
                }
            }
            // 查询流程
            WorkReport wr = new WorkReport();
            String nextProcessPerson = findNextProcessPerson(processUid, releaseUid, null, null);
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            wr.setUid(uid);
            wr.setReleaseUid(releaseUid);
            wr.setNextUid(nextProcessPerson);
            int result = workReportService.addWorkReport(wr);
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

    // 通过申请人查询本周是否提交过工作报告
    @RequestMapping(value = "/checkLastWeekWorkReport", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkLastWeekWorkReport(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 判断是否提交过工作报告
            WorkReport workReport = workReportService.checkLastWeekWorkReport(releaseUid);
            if (workReport != null) {
                // 根据 create_time 判断提交时间是否是上周一到上周日
                String createTime = workReport.getCreate_time();
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
    @RequestMapping(value = "/findWorkReportList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWorkReportList(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String releaseUid = JwtUtil.getUserUid(token);
            // 根据提交人查询数据库
            List<WorkReport> list = workReportService.findWorkReportList(releaseUid);
            if (list.size() != 0) {
                return new Message_body(200, "查询部门变更申请记录成功", list);
            } else {
                return new Message_body(300, "暂无记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 查询审批流程
    @RequestMapping(value = "/findWorkReportByTeacherProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWorkReportByTeacherProcess(String uid) {
        try {
            // 通过接收到的 uid 查询本条申请记录
            WorkReport workReport = workReportService.findWorkReportByUid(uid);
            // 查询审批流程
            String[] pros = getProcess(processUid, workReport.getReleaseUid(), null);
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

    // 删除提交的工作报告
    @RequestMapping(value = "/deleteWorkReportByTeacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message deleteWorkReportByTeacher(HttpServletRequest request, String uid) {
        try {
            String tableUid = request.getHeader("tableUid");
            // 通过接收到的 uid 查询本条申请记录
            WorkReport workReport = workReportService.findWorkReportByUid(uid);
            // 判断是否为提交人
            if (workReport.getReleaseUid().equals(JwtUtil.getUserUid(request.getHeader("Authorization")))) {
                // 删除本条申请记录
                int i = workReportService.deleteByUid(uid);
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

    // 根据下一级审批人查询工作报告记录
    @RequestMapping(value = "/findWorkReportByNextUid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findWorkReportByNextUid(HttpServletRequest request) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nextUid = JwtUtil.getUserUid(token);
            // 根据下一级审批人查询数据库
            List<WorkReport> list = workReportService.findWorkReportWaitList(nextUid);
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
    @RequestMapping(value = "/resolveWorkReport", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message resolveWorkReport(HttpServletRequest request, String uid) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 通过 uid 查询本条申请记录
            WorkReport workReport = workReportService.findWorkReportByUid(uid);
            // 判断是否为下一级审批人
            if (!workReport.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 查询审批流程
            String[] props = getProcess(processUid, workReport.getReleaseUid(), teacherService.findDepartmentUid(workReport.getReleaseUid()));// 判断是否为最后一级审批人
            if (nowUid.equals(props[props.length - 1])) {
                // 如果是最后一级审批人，审批通过
                workReport.setStatus(1);
                int i = workReportService.resolveWorkReport(workReport);
                if (i > 0) {
                    return new Message(200, "审批通过");
                } else {
                    return new Message(400, "审批失败");
                }
            } else {
                // 如果不是最后一级审批人，将下一级审批人改为下一级审批人
                String nextProcessPerson = findNextProcessPerson(processUid, workReport.getReleaseUid(), nowUid, null);
                workReport.setNextUid(nextProcessPerson);
                workReport.setCount(workReport.getCount() + 1);
                int i = workReportService.resolveWorkReport(workReport);
                if (i > 0) {
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

    // 驳回工作报告
    @RequestMapping(value = "/rejectWorkReport", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message rejectWorkReport(HttpServletRequest request, WorkReport workReport) {
        try {
            // 解析 token，获取 uid
            String token = request.getHeader("Authorization");
            String nowUid = JwtUtil.getUserUid(token);
            // 通过 uid 查询本条申请记录
            WorkReport apply = workReportService.findWorkReportByUid(workReport.getUid());
            // 判断是否为下一级审批人
            if (!apply.getNextUid().equals(nowUid)) {
                return new Message(403, "只能审批自己的申请");
            }
            // 驳回
            int i = workReportService.rejectWorkReport(workReport);
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
}
