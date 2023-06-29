// StudentService.java
package com.scholarship.service;

import com.scholarship.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(int pageSize, int currentPage);

    int getTotalStudents();

    Student saveStudent(Student student);
    Student getStudentById(int id);


    void updateStudent(Student student);

    void deleteStudent(int id);
}
