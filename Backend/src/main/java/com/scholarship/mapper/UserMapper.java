package com.scholarship.mapper;

import com.scholarship.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
