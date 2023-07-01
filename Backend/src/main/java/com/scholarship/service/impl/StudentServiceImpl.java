// StudentServiceImpl.java
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
        Student existingStudent = studentMapper.getStudentById(student.getId());
        if (existingStudent != null) {
            studentMapper.updateStudent(student);
            existingStudent = studentMapper.getStudentById(student.getId());
            return existingStudent;
        } else {
            studentMapper.addStudent(student);
            return studentMapper.getStudentById(student.getId());
        }
    }


    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentMapper.deleteStudent(id);
        Student student = studentMapper.getStudentById(id);
        if (student != null) {
            throw new RuntimeException("学生删除失败");
        }
    }

    @Override
    public Student getStudentById(int id) {
        return studentMapper.getStudentById(id);
    }


    @Override
    public List<Student> searchStudents(Student searchForm) {
        return studentMapper.searchStudents(searchForm);
    }
}
