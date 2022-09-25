package com.cshbxy.controller;

import com.cshbxy.domain.Department;
import com.cshbxy.domain.Leader;
import com.cshbxy.domain.Message;
import com.cshbxy.mapper.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/query")
@CrossOrigin(origins = "*")
public class QueryController {

    @Autowired
    private QueryMapper queryMapper;

    @RequestMapping(value = "/teacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message teacherUsernameQuery(String username) {
        try {
            if (queryMapper.teacherUsernameQuery(username) != null) {
                return new Message(300, "用户名已存在");
            }
            return new Message(200, "用户名可用");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/department", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message departmentUsernameQuery(String username) {
        try {
            if (queryMapper.departmentUsernameQuery(username) != null) {
                return new Message(300, "用户名已存在");
            }
            return new Message(200, "用户名可用");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/leader", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message leaderUsernameQuery(String username) {
        try {
            if (queryMapper.leaderUsernameQuery(username) != null) {
                return new Message(300, "用户名已存在");
            }
            return new Message(200, "用户名可用");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/departmentMessage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message departmentMessage() {
        try {
            List<Department> departments = queryMapper.departmentMessage();
            // 如果数组中有数据，返回 200和数据，否则返回 400
            if (departments.size() != 0) {
                return new Message(200, "获取部门列表成功", departments);
            }
            return new Message(400, "获取部门列表失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    @RequestMapping(value = "/leaderMessage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message leaderMessage() {
        try {
            List<Leader> leaders = queryMapper.leaderMessage();
            // 如果数组中有数据，返回 200和数据，否则返回 400
            if (leaders.size() != 0) {
                return new Message(200, "获取领导列表成功", leaders);
            }
            return new Message(400, "获取领导列表失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }
}
