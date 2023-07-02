//package com.scholarship.controller;
//
//import com.scholarship.entity.Applicant;
//import com.scholarship.service.ApplicantService;
//import com.scholarship.utils.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URLDecoder;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/applicants")
//public class ApplicantController {
//    private final ApplicantService applicantService;
//
//    @Autowired
//    public ApplicantController(ApplicantService applicantService) {
//        this.applicantService = applicantService;
//    }
//
//    @PostMapping
//    public ResponseEntity<Result<List<Applicant>>> getApplicantsByGrade(@RequestBody Map<String, String> requestBody) {
//        try {
//            String grade = requestBody.get("grade");
//            String decodedGrade = URLDecoder.decode(grade, "UTF-8");
//
//            System.out.println(grade);
//            System.out.println(decodedGrade);
//            List<Applicant> applicants = applicantService.getApplicantsByGrade(grade);
//            System.out.println(applicants);
//            return ResponseEntity.ok(new Result<>(200, "成功获取学生数据", applicants));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取学生数据失败", null));
//        }
//    }
//
//
//    @DeleteMapping("/{studentId}")
//    public ResponseEntity<Result<Object>> deleteApplicant(@PathVariable("studentId") String studentId) {
//        try {
//            applicantService.deleteApplicant(studentId);
//            return ResponseEntity.ok(new Result<>(200, "学生已删除", null));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "删除学生失败", null));
//        }
//    }
//
//    // Add mappings for any other necessary methods
//}
