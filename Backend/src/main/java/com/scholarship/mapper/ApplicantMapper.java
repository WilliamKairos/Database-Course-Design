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
}
