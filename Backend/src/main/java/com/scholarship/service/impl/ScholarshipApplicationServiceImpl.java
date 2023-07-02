package com.scholarship.service.impl;

import com.scholarship.entity.ScholarshipApplication;
import com.scholarship.mapper.ScholarshipApplicationMapper;
import com.scholarship.service.ScholarshipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScholarshipApplicationServiceImpl implements ScholarshipApplicationService {
    private final ScholarshipApplicationMapper applicationMapper;

    @Autowired
    public ScholarshipApplicationServiceImpl(ScholarshipApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    @Override
    public void submitScholarshipApplication(ScholarshipApplication application) {
        applicationMapper.insertScholarshipApplication(application);
    }
}
