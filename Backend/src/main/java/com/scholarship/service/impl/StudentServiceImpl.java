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

    @Override
    public Student saveStudent(Student student) {
        Student existingStudent = studentMapper.getStudentById(student.getStudentId());
        if (existingStudent != null) {
            studentMapper.updateStudent(student);
            existingStudent = studentMapper.getStudentById(student.getStudentId());
            return existingStudent;
        } else {
            studentMapper.addStudent(student);
            return studentMapper.getStudentById(student.getStudentId());
        }
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    @Override
    public void deleteStudent(String studentId) {
        studentMapper.deleteStudent(studentId);
        Student student = studentMapper.getStudentById(studentId);
        if (student != null) {
            throw new RuntimeException("学生删除失败");
        }
    }

    @Override
    public Student searchStudentById(String searchId) {
        return studentMapper.getStudentById(searchId);
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentMapper.getStudentById(studentId);
    }

    @Override
    public List<Student> searchStudents(Student searchForm) {
        return studentMapper.searchStudents(searchForm);
    }
}
