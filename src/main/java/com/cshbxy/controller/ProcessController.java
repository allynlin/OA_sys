package com.cshbxy.controller;

import com.cshbxy.Util.findRealeName;
import com.cshbxy.dao.Message_body;
import com.cshbxy.dao.Process;
import com.cshbxy.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/process")
@CrossOrigin(origins = "*")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    // 查询所有流程
    @RequestMapping(value = "/findAllProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findAllProcess() {
        try {
            List<Process> list = processService.findAllProcess();
            for (Process process : list) {
                String[] processList = process.getProcess().split("\\|\\|");
                // 将流程中的部门 uid 替换为部门名称
                for (int i = 0; i < processList.length; i++) {
                    String realName = findRealeName.findName(processList[i]);
                    if (realName != null) {
                        processList[i] = realName;
                    }
                }
                process.setProcessRealName(String.join("||", processList));
            }
            return new Message_body(200, "获取成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "系统错误");
        }
    }

    // 更新流程
    @RequestMapping(value = "/updateProcess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body updateProcess(Process process) {
        try {
            int result = processService.processUpdate(process);
            if (result == 1) {
                return new Message_body(200, "修改成功");
            } else {
                return new Message_body(500, "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "系统错误");
        }
    }
}