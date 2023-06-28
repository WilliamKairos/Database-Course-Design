package com.scholarship.entity;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String studentId;
    private String avatar;
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String grade;
    private String major;
}
