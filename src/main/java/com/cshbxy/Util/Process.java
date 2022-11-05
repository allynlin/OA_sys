package com.cshbxy.Util;

import com.cshbxy.service.ProcessService;
import com.cshbxy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Process {
    private static ProcessService processService;

    @Autowired
    public void setProcessService(ProcessService processService) {
        Process.processService = processService;
    }

    private static TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        Process.teacherService = teacherService;
    }

    public static String getProcess(String processName, String releaseUid) {
        // 通过 uid 查询变更部门申请流程
        com.cshbxy.dao.Process process = processService.processQueryByName(processName);
        String pro = process.getProcess();
        // 如果字符串中有 nowDepartment
        if (pro.contains("nowDepartment")) {
            // 将 nowDepartment 替换为当前部门
            pro = pro.replace("nowDepartment", teacherService.findDepartmentUid(releaseUid));
        }
        return pro;
    }

    public static String getProcessFirst(String process) {
        String[] processList = process.split("\\|\\|");
        return processList[0];
    }
}