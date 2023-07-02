package com.scholarship.service;

import com.scholarship.entity.Applicant;

import java.util.List;

public interface ApplicantService {
    List<Applicant> getApplicantsByGrade(String grade);

    void deleteApplicant(String studentId);

    // Add any other necessary methods
}
