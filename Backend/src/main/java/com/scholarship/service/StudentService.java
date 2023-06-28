package com.scholarship.service;

import com.scholarship.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(int pageSize, int currentPage);

    int getTotalStudents();
}
