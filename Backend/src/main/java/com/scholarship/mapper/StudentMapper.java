package com.scholarship.mapper;

import com.scholarship.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> getStudents(@Param("pageSize") int pageSize, @Param("offset") int offset);

    int getTotalStudents();

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(String studentId);

    Student getStudentById(@Param("studentId") String studentId);

    List<Student> searchStudents(@Param("searchForm") Student searchForm);
}
