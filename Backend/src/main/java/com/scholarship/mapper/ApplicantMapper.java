package com.scholarship.mapper;

import com.scholarship.entity.Applicant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicantMapper {
    List<Applicant> getApplicantsByGrade(@Param("grade") String grade);

    void deleteApplicant(@Param("studentId") String studentId);

    // Add any other necessary methods
    void saveScholarship(@Param("param1") Applicant request);

    void updateApprovalStatus(@Param("param1") Applicant request);

    void updateStudentScores(@Param("param1") Applicant request);
}
