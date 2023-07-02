package com.scholarship.service;

import com.scholarship.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(int pageSize, int currentPage);
    List<Student> searchStudents(Student searchForm);
    int getTotalStudents();

    Student saveStudent(Student student);
    Student getStudentById(String studentId); // 改变参数类型为String

    void updateStudent(Student student);

    void deleteStudent(String studentId); // 改变参数类型为String

    Student searchStudentById(String searchId);

}
