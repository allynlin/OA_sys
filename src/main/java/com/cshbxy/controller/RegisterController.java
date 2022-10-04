package com.cshbxy.controller;

import com.cshbxy.dao.Department;
import com.cshbxy.dao.Leader;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Teacher;
import com.cshbxy.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    // 登录,post 请求，x-www-form-urlencoded 传参，返回 json
    @RequestMapping(value = "/teacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message teacherRegister(Teacher teacher) {
        try {
            // 生成 uuid
            teacher.setUid(UUID.randomUUID().toString());
            int result = registerService.teacherRegister(teacher);
            if (result != 1) {
                return new Message(400, "注册失败");
            }
            return new Message(200, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/department", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message departmentRegister(Department department) {
        try {
            // 生成 uuid
            department.setUid(UUID.randomUUID().toString());
            int result = registerService.departmentRegister(department);
            if (result != 1) {
                return new Message(400, "注册失败");
            }
            return new Message(200, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/leader", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message leaderRegister(Leader leader) {
        try {
            leader.setUid(UUID.randomUUID().toString());
            int result = registerService.leaderRegister(leader);
            if (result != 1) {
                return new Message(400, "注册失败");
            }
            return new Message(200, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}
