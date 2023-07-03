package com.scholarship.service.impl;

import com.scholarship.entity.Applicant;
import com.scholarship.mapper.ApplicantMapper;
import com.scholarship.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantMapper applicantMapper;

    @Autowired
    public ApplicantServiceImpl(ApplicantMapper applicantMapper) {
        this.applicantMapper = applicantMapper;
    }

    @Override
    public List<Applicant> getApplicantsByGrade(String grade) {
        return applicantMapper.getApplicantsByGrade(grade);
    }

    @Override
    public void deleteApplicant(String studentId) {
        applicantMapper.deleteApplicant(studentId);
    }

    @Override
    public void submitApplicant(Applicant applicant) {

    }

    public void applyScholarship(Applicant request) {
        // 在这里进行奖学金申请的保存操作，可以调用相应的Mapper方法
        applicantMapper.saveScholarship(request);
    }

    // Add implementations for any other necessary methods
}
