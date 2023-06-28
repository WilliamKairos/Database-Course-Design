package com.scholarship.mapper;

import com.scholarship.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    @Select("SELECT * FROM students LIMIT #{pageSize} OFFSET #{offset}")
    List<Student> getStudents(@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM students")
    int getTotalStudents();
}
