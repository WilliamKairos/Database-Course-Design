package com.scholarship.entity;

import lombok.Data;

import javax.persistence.Column;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    @Column(name = "user_type")
    private String userType;

//    private String userType;

    public User() {
        // 默认构造函数
    }
//    public String getUserType() {
//        // 返回用户的身份类型，确保返回非空的字符串
//        return userType;
//    }
}

//刚才的贡献度没记录上，这个是记录贡献点的