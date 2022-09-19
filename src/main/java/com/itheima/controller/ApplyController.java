package com.itheima.controller;

import com.itheima.domain.Message;
import com.itheima.domain.Teacher;
import com.itheima.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/apply")
@CrossOrigin(origins = "*")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    // 登录,post 请求，x-www-form-urlencoded 传参，返回 json
//    @RequestMapping(value = "/teacherChangeDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Message teacherRegister(Teacher teacher) {
//        try {
//            // 生成 uuid
//            teacher.setUid(UUID.randomUUID().toString());
//            int result = applyService.teacherChangeDepartment(teacher);
//            if (result != 1) {
//                return new Message(400, "提交失败");
//            }
//            return new Message(200, "提交成功");
//        } catch (Exception e) {
//            return new Message(500, "未知错误");
//        }
//    }
}
