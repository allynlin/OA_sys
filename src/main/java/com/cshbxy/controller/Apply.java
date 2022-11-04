package com.cshbxy.controller;

import com.cshbxy.service.DepartmentChangeService;
import com.cshbxy.service.FileUploadService;
import com.cshbxy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apply")
@CrossOrigin(origins = "*")
public class Apply {
    @Autowired
    private DepartmentChangeService departmentChangeService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private FileUploadService fileUploadService;

}
