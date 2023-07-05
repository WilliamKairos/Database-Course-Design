package com.scholarship.service;

import com.scholarship.entity.Applicant;

import java.util.List;

public interface ApplicantService {
    void submitApplicant(Applicant applicant);
    List<Applicant> getApplicantsByGrade(String grade);
    void deleteApplicant(String studentId);
    void applyScholarship(Applicant request);

    void updateApprovalStatus(Applicant request);
    void updateStudentScores(Applicant request);
}
