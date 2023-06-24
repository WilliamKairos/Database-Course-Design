package com.scholarship.mapper;

import com.scholarship.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
