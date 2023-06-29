package com.scholarship.controller;

import com.scholarship.entity.Student;
import com.scholarship.service.StudentService;
import com.scholarship.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Result<List<Student>> getStudents(@RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(defaultValue = "1") int currentPage) {
        try {
            List<Student> students = studentService.getStudents(pageSize, currentPage);
//            int totalItems = studentService.getTotalStudents();
            return new Result<>(200, "成功获取学生数据", students);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(500, "获取学生数据失败", null);
        }
    }
}
