package com.scholarship.controller;

import com.scholarship.entity.Student;
import com.scholarship.service.ApplicantService;
import com.scholarship.service.StudentService;
import com.scholarship.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    private final ApplicantService applicantService;

    @Autowired
    public StudentController(StudentService studentService, ApplicantService service) {
        this.studentService = studentService;
        this.applicantService = service;
    }

    @PostMapping("/getStudent")
    public ResponseEntity<Result<Map<String, Object>>> getStudents(@RequestBody Map<String, Integer> requestData) {
        int pageSize = requestData.getOrDefault("pageSize", 10);
        int currentPage = requestData.getOrDefault("currentPage", 1);
        try {
            List<Student> students = studentService.getStudents(pageSize, currentPage);
            int totalStudents = studentService.getTotalStudents();
            Map<String, Object> response = new HashMap<>();
            response.put("students", students);
            response.put("total", totalStudents);
            return new ResponseEntity<>(new Result<>(200, "成功获取学生数据", response), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "获取学生数据失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveStudent")
    public ResponseEntity<Result<Student>> saveStudent(@RequestBody Student student) {
        try {
            student.setDeleted(false);
            Student savedStudent = studentService.saveStudent(student);
            return new ResponseEntity<>(new Result<>(200, "成功保存学生信息", savedStudent), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "保存学生信息失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Result<Student>> updateStudent(@PathVariable("studentId") String studentId, @RequestBody Student updatedStudent) {
        try {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return new ResponseEntity<>(new Result<>(404, "学生不存在", null), HttpStatus.NOT_FOUND);
            }

            // 更新学生信息
            student.setName(updatedStudent.getName());
            student.setGender(updatedStudent.getGender());
            student.setPhoneNumber(updatedStudent.getPhoneNumber());
            student.setEmail(updatedStudent.getEmail());
            student.setGrade(updatedStudent.getGrade());
            student.setMajor(updatedStudent.getMajor());

            studentService.updateStudent(student); // 保存更新后的学生信息

            return new ResponseEntity<>(new Result<>(200, "学生信息已更新", student), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "更新学生信息失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Result<Object>> deleteStudent(@PathVariable("studentId") String studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return new ResponseEntity<>(new Result<>(404, "学生不存在", null), HttpStatus.NOT_FOUND);
            }

            // 删除与该学生相关的申请信息
//            studentService.deleteApplicantByStudentId(studentId);
            applicantService.deleteApplicant(studentId);

            // 逻辑删除学生
            student.setDeleted(true);
            studentService.updateStudent(student);

//            // 删除学生
//            studentService.deleteStudent(studentId);

            return new ResponseEntity<>(new Result<>(200, "学生已删除", null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "删除学生失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Result<List<Student>>> searchStudents(@RequestBody Student searchForm) {
        try {
            List<Student> students = studentService.searchStudents(searchForm);
            return new ResponseEntity<>(new Result<>(200, "成功查询学生", students), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "查询学生失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/info")
    public ResponseEntity<Result<Student>> searchStudentById(@RequestBody Student searchId) {
        try {
            System.out.println(searchId.getStudentId());
            Student students = studentService.searchStudentById(searchId.getStudentId());
            System.out.println(students);
            return new ResponseEntity<>(new Result<>(200, "成功查询学生", students), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "查询学生失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

