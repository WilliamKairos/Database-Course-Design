package com.scholarship.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String userType;

    public User() {
        // 默认构造函数
    }
}
