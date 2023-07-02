package com.scholarship.mapper;

import com.scholarship.entity.ScholarshipApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ScholarshipApplicationMapper {
    void insertScholarshipApplication(ScholarshipApplication application);
}
