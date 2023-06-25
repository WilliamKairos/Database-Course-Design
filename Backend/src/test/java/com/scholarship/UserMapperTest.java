package com.scholarship;
import com.scholarship.mapper.UserMapper;
import com.scholarship.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetAllUsers() {
        List<User> users = userMapper.getAllUsers();
        for (User user : users) {
            System.out.println("用户名：" + user.getUsername());
            System.out.println("密码：" + user.getPassword());
        }
    }
}
