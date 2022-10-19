package com.cshbxy.Util;

import com.cshbxy.service.DepartmentService;
import com.cshbxy.service.LeaderService;
import com.cshbxy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class findRealeName {

    private static DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        findRealeName.departmentService = departmentService;
    }

    private static TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        findRealeName.teacherService = teacherService;
    }

    private static LeaderService leaderService;

    @Autowired
    public void setLeaderService(LeaderService leaderService) {
        findRealeName.leaderService = leaderService;
    }

    public static String findName(String uid) {
        // 将 uid 依次在 department, leader, teacher 表中查找，找到返回真实姓名，否则返回 null, 用于判断是否存在该用户
        if (departmentService.findRealeName(uid) != null) {
            return departmentService.findRealeName(uid);
        } else if (leaderService.findRealeName(uid) != null) {
            return leaderService.findRealeName(uid);
        } else if (teacherService.findRealeName(uid) != null) {
            return teacherService.findRealeName(uid);
        } else {
            return null;
        }
    }
}
