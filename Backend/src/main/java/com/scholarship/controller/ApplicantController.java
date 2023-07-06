package com.scholarship.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarship.entity.Applicant;
import com.scholarship.entity.ApplicantDTO;
import com.scholarship.service.ApplicantService;
import com.scholarship.utils.AliOSSUtils;
import com.scholarship.utils.Result;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {
    private final ApplicantService applicantService;
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Result<List<Applicant>>> getApplicantsByGrade(@RequestBody Map<String, String> requestBody) {
        try {
            String grade = requestBody.get("grade");
            String decodedGrade = URLDecoder.decode(grade, "UTF-8");

//            System.out.println(grade);
//            System.out.println(decodedGrade);
            List<Applicant> applicants = applicantService.getApplicantsByGrade(grade);
//            System.out.println(applicants);
            return ResponseEntity.ok(new Result<>(200, "成功获取学生数据", applicants));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取学生数据失败", null));
        }
    }


    @DeleteMapping("/{studentId}")
    public ResponseEntity<Result<Object>> deleteApplicant(@PathVariable("studentId") String studentId) {
        try {
            applicantService.deleteApplicant(studentId);
            return ResponseEntity.ok(new Result<>(200, "学生已删除", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "删除学生失败", null));
        }
    }




    @PostMapping("/apply")
    public ResponseEntity<Result<Void>> applyScholarship(@RequestBody ApplicantDTO request) {
        try {
            String grade = request.getGrade();
            if ("研一年级".equals(grade)) {
                request.setGrade("grade1");
            } else if ("研二年级".equals(grade)) {
                request.setGrade("grade2");
            } else if ("研三年级".equals(grade)) {
                request.setGrade("grade3");
            }

            System.out.println(request);

            ObjectMapper mapper = new ObjectMapper();
            Applicant applicant = new Applicant();
            applicant.setStudentId(request.getStudentId());
            applicant.setName(request.getName());
            applicant.setGrade(request.getGrade());
            applicant.setScholarshipType(request.getScholarshipType());
            applicant.setApplicationTime(request.getApplicationTime());
            applicant.setTotalPoints(request.getTotalPoints());

            if (request.getApplicationMaterial() != null) {
                String applicationMaterial = mapper.writeValueAsString(request.getApplicationMaterial());
                applicant.setApplicationMaterial(applicationMaterial);
            }
            if (request.getPapers() != null) {
                String papers = mapper.writeValueAsString(request.getPapers());
                applicant.setPapers(papers);
            }
            if (request.getCompetitions() != null) {
                String competitions = mapper.writeValueAsString(request.getCompetitions());
                applicant.setCompetitions(competitions);
            }

            System.out.println(request);
            System.out.println("------------");
            System.out.println(applicant);
            applicantService.applyScholarship(applicant);
            return new ResponseEntity<>(new Result<>(200, "奖学金申请提交成功", null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "奖学金申请提交失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<Result<String>> handleFileUpload(@RequestParam("file") List<MultipartFile> files){
        try {
            List<String> filePaths = new ArrayList<>();
            for (MultipartFile file : files){
//                String originalFilename = file.getOriginalFilename();
                String url = aliOSSUtils.upload(file);
                filePaths.add(url);
                System.out.println(url);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String applicationMaterial = objectMapper.writeValueAsString(Map.of("filePaths", filePaths));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            return new ResponseEntity<>(new Result<>(200, "上传成功", applicationMaterial), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result<>(500, "奖学金申请提交失败", null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<Result<String>> handleFileUpload(@RequestParam("file") List<MultipartFile> files) {
//        try {
//            // 指定保存文件的文件夹路径
//            String uploadFolder = "/Users/wushangyuan/Downloads";
//
//            // 用于存储所有文件的路径
//            List<String> filePaths = new ArrayList<>();
//
//            // 遍历所有文件并保存到指定路径
//            for (MultipartFile file : files) {
//                String fileName = generateFileName(file.getOriginalFilename());
//                Path filePath = Path.of(uploadFolder, fileName);
//                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//                String filePathString = filePath.toString();
//                filePaths.add(filePathString);
//            }
//
//            // 构建附件信息，将文件路径存储到 applicationMaterial 属性中
//            String applicationMaterial = buildApplicationMaterial(filePaths);
//            return new ResponseEntity<>(new Result<>(200, "文件上传成功", applicationMaterial), HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new Result<>(500, "文件上传失败", null));
//        }
//    }
//
//    // 更新 buildApplicationMaterial 方法以处理多个文件路径
//    private String buildApplicationMaterial(List<String> filePaths) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(Map.of("filePaths", filePaths));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    // 生成唯一的文件名
//    private String generateFileName(String originalFilename) {
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String extension = getFileExtension(originalFilename);
//        return timestamp + extension;
//    }
//
//    // 获取文件扩展名
//    private String getFileExtension(String filename) {
//        int dotIndex = filename.lastIndexOf(".");
//        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
//            return filename.substring(dotIndex);
//        }
//        return "";
//    }
//
//        // 构建附件信息
//    private String buildApplicationMaterial(String filePath) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(Map.of("filePath", filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
    @PostMapping("/approval")
    public ResponseEntity<Result<Void>> updateApprovalStatus(@RequestBody Applicant request) {
        try {
            applicantService.updateApprovalStatus(request);

            return ResponseEntity.ok(new Result<>(200, "审批状态更新成功", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result<>(500, "审批状态更新失败", null));
        }
    }

    @PostMapping("/scores")
    public ResponseEntity<Result<Void>> updateStudentScores(@RequestBody Applicant request) {
        try {
            applicantService.updateStudentScores(request);

            return ResponseEntity.ok(new Result<>(200, "学生成绩更新成功", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result<>(500, "学生成绩更新失败", null));
        }
    }
}
