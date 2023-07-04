package com.scholarship.entity;

import lombok.Data;

import java.util.List;

@Data
public class ApplicantDTO {
    private String studentId;
    private String name;
    private String grade;
    private Integer academicScore;
    private Integer ideologyScore;
    private Integer researchScore;
    private Integer socialScore;
    private String scholarshipType;
    private List<String> applicationMaterial; // 修改为 List<String> 类型
    //    private String applicationMaterial; // 修改为 List<String> 类型
    private String academicEvaluation;
    private String applicationTime;
    private String reason;
    // Add any other necessary fields
}
