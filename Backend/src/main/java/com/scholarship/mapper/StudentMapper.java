package com.scholarship.mapper;

import com.scholarship.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    //@Select("SELECT * FROM students LIMIT #{pageSize} OFFSET #{offset}")
    List<Student> getStudents(@Param("pageSize") int pageSize, @Param("offset") int offset);

    //@Select("SELECT COUNT(*) FROM students")
    int getTotalStudents();

//    @Insert("INSERT INTO students (studentId, name, gender, phoneNumber, email, grade, major) " +
//            "VALUES (#{studentId}, #{avatar}, #{name}, #{gender}, #{phoneNumber}, #{email}, #{grade}, #{major})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addStudent(Student student);

//    @Update("UPDATE students SET studentId = #{studentId}, avatar = #{avatar}, name = #{name}, " +
//            "gender = #{gender}, phoneNumber = #{phoneNumber}, email = #{email}, grade = #{grade}, " +
//            "major = #{major} WHERE id = #{id}")
    void updateStudent(Student student);

//    @Delete("DELETE FROM students WHERE id = #{id}")
    void deleteStudent(int id);

//    @Select("SELECT * FROM students WHERE id = #{id}")
    Student getStudentById(@Param("id") int id);
//}
}
