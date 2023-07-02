package com.scholarship.entity;

import lombok.Data;

@Data
public class Applicant {
    private String studentId;
    private String name;
    private Integer academicScore;
    private Integer ideologyScore;
    private Integer researchScore;
    private Integer socialScore;
    private String scholarshipType;
    private String academicEvaluation;
    private String applicationTime;
    private String reason;
    // Add any other necessary fields
}
