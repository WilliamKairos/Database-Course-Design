package com.scholarship.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Applicant {
    private String studentId;
    private String name;
    private String grade;
    private Integer academicScore;
    private Integer ideologyScore;
    private Integer researchScore;
    private Integer socialScore;
    private String scholarshipType;
//    private List<String> applicationMaterial; // 修改为 List<String> 类型
    private String applicationMaterial; // 修改为 List<String> 类型
    private String academicEvaluation;
    private String applicationTime;
    private String papers;
    private String competitions;
    private String reason;
    // Add any other necessary fields
}
