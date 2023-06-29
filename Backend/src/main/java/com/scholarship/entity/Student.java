package com.scholarship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String studentId;
//    private String avatar;
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String grade;
    private String major;
}
