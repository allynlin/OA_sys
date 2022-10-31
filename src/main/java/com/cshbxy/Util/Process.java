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

    public static String[] getProcess(String uid, String releaseUid, String departmentUid) {
        // 通过 uid 查询变更部门申请流程
        com.cshbxy.dao.Process process = processService.processQueryByUid(uid);
        String pro = process.getProcess();
        // 将查询到的数据分离，以 || 分隔
        String[] pros = pro.split("\\|\\|");
        // 修改数组中的 nowDepartment 为当前部门，nextDepartment 为下一部门
        for (int i = 0; i < pros.length; i++) {
            if (pros[i].equals("nowDepartment")) {
                pros[i] = teacherService.findDepartmentUid(releaseUid);
                continue;
            }
            // 如果传递了 departmentUid,则执行后续判断
            if (departmentUid != null) {
                if (pros[i].equals("nextDepartment")) {
                    pros[i] = departmentUid;
                }
            }
        }
        return pros;
    }

    // 查找下一级审批人
    public static String findNextProcessPerson(String uid, String releaseUid, String nextUid, String departmentUid) {
        try {
            String[] pros = getProcess(uid, releaseUid, departmentUid);
            if (nextUid == null) {
                return pros[0];
            }
            // 遍历数组，根据 NowUid 寻找下一级审批人
            for (int i = 0; i < pros.length; i++) {
                if (pros[i].equals(nextUid)) {
                    return pros[i + 1];
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}