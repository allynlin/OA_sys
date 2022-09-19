package com.itheima.controller;

import com.itheima.Util.CheckToken;
import com.itheima.Util.JwtUtil;
import com.itheima.domain.Department;
import com.itheima.domain.Leader;
import com.itheima.domain.Message;
import com.itheima.domain.Teacher;
import com.itheima.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 登录,post 请求，x-www-form-urlencoded 传参，返回 json
    @RequestMapping(value = "/teacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message teacherLogin(String username, String password) {
        try {
            Teacher teacher = loginService.teacherLogin(username, password);
            if (teacher != null) {
                switch (teacher.getStatus()) {
                    case -1:
                        return new Message(-1, "账号已被禁用");
                    case 0:
                        // 生成 token，附带用户类型和用户 uid
                        String token = JwtUtil.sign(teacher.getUid(), "teacher");
                        return new Message(200, "登录成功", teacher, token);
                    default:
                        return new Message(400, "账号异常");
                }
            } else {
                return new Message(400, "登录失败");
            }
        } catch (Exception e) {
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/department", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message departmentLogin(String username, String password) {
        try {
            Department department = loginService.departmentLogin(username, password);
            if (department != null) {
                switch (department.getStatus()) {
                    case -1:
                        return new Message(-1, "账号已被禁用");
                    case 0:
                        // 生成 token，附带用户类型和用户 uid
                        String token = JwtUtil.sign(department.getUid(), "department");
                        return new Message(200, "登录成功", department, token);
                    default:
                        return new Message(400, "账号异常");
                }
            } else {
                return new Message(400, "登录失败");
            }
        } catch (Exception e) {
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/leader", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message leaderLogin(String username, String password) {
        try {
            Leader leader = loginService.leaderLogin(username, password);
            if (leader != null) {
                switch (leader.getStatus()) {
                    case -1:
                        return new Message(-1, "账号已被禁用");
                    case 0:
                        // 生成 token，附带用户类型和用户 uid
                        String token = JwtUtil.sign(leader.getUid(), "leader");
                        return new Message(200, "登录成功", leader, token);
                    default:
                        return new Message(400, "账号异常");
                }
            } else {
                return new Message(400, "登录失败");
            }
        } catch (Exception e) {
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/checkTeacher", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkTeacher(HttpServletRequest request) {
        try {
            // 获取请求头 Authorization 中的 token
            String token = request.getHeader("Authorization");
            if (CheckToken.isTeacher(token) != null) {
                return new Message(200, "身份验证成功", null, CheckToken.isTeacher(token));
            } else {
                return new Message(403, "身份验证失败");
            }
        } catch (Exception e) {
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/checkDepartment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkDepartment(HttpServletRequest request) {
        try {
            // 获取请求头 Authorization 中的 token
            String token = request.getHeader("Authorization");
            if (CheckToken.isDepartment(token) != null) {
                return new Message(200, "身份验证成功", null, CheckToken.isDepartment(token));
            } else {
                return new Message(403, "身份验证失败");
            }
        } catch (Exception e) {
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/checkLeader", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkLeader(HttpServletRequest request) {
        try {
            // 获取请求头 Authorization 中的 token
            String token = request.getHeader("Authorization");
            if (CheckToken.isLeader(token) != null) {
                return new Message(200, "身份验证成功", null, CheckToken.isLeader(token));
            } else {
                return new Message(403, "身份验证失败");
            }
        } catch (Exception e) {
            return new Message(500, "未知错误");
        }
    }
}
