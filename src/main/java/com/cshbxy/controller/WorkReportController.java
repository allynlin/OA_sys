package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.Process;
import com.cshbxy.dao.*;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.ProcessService;
import com.cshbxy.service.TeacherService;
import com.cshbxy.service.WorkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/workReport")
@CrossOrigin(origins = "*")
public class WorkReportController {

    @Autowired
    private WorkReportService workReportService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ProcessService processService;

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

    public String[] getProcess(WorkReport workReport) {
        // 通过 uid 查询变更部门申请流程
        Process process = processService.processQueryByUid("5c4a2ce8-6c37-402b-bff3-998da0cc3376");
        String pro = process.getProcess();
        // 将查询到的数据分离，以 || 分隔
        String[] pros = pro.split("\\|\\|");
        // 修改数组中的 nowDepartment 为当前部门
        for (int i = 0; i < pros.length; i++) {
            if (pros[i].equals("nowDepartment")) {
                pros[i] = teacherService.findDepartmentUid(workReport.getReleaseUid());
            }
        }
        return pros;
    }

    // 查找下一级审批人
    public String findNextProcessPerson(WorkReport workReport) {
        try {
            String[] pros = getProcess(workReport);
            if (workReport.getNextUid() == null) {
                return pros[0];
            }
            // 遍历数组，根据 NowUid 寻找下一级审批人
            for (int i = 0; i < pros.length; i++) {
                if (pros[i].equals(workReport.getNextUid())) {
                    return pros[i + 1];
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            wr.setReleaseUid(releaseUid);
            String nextProcessPerson = findNextProcessPerson(wr);
            // 生成 uuid
            String uid = UUID.randomUUID().toString();
            wr.setUid(uid);
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
            String[] pros = getProcess(workReport);
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
}
