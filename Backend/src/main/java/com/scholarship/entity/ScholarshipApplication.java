package com.scholarship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScholarshipApplication {
    private String studentId;
    private String name;
    private String grade;
    private String major;
    private String scholarshipType;
    private String applicationDate;
}
