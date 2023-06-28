package com.scholarship.service.impl;

import com.scholarship.entity.Student;
import com.scholarship.mapper.StudentMapper;
import com.scholarship.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> getStudents(int pageSize, int currentPage) {
        int offset = (currentPage - 1) * pageSize;
        return studentMapper.getStudents(pageSize, offset);
    }

    @Override
    public int getTotalStudents() {
        return studentMapper.getTotalStudents();
    }
}
