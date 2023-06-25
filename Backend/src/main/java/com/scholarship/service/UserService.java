//package com.scholarship.service;
//
//import com.scholarship.utils.Result;
//import com.scholarship.entity.User;
//import com.scholarship.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    private final UserMapper userMapper;
//
//    @Autowired
//    public UserService(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
//
//    public Result<User> login(String username, String password) {
//        User user = userMapper.findUserByUsernameAndPassword(username, password);
//        if (user != null) {
//            return new Result<>(200, "登录成功", user);
//        } else {
//            return new Result<>(401, "用户名或密码错误", null);
//        }
//    }
//}
package com.scholarship.service;

import com.scholarship.utils.Result;
import com.scholarship.entity.User;
import com.scholarship.mapper.UserMapper;
import com.scholarship.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Result<User> findUserByUsernameAndPassword(String username, String password) {
        User user = userMapper.findUserByUsernameAndPassword(username, password);
        if (user != null) {
            Result<User> result = new Result<>(200, "登录成功", user);
            result.setData(user);
            return result;
        } else {
            return new Result<>(401, "用户名密码错误", null);
        }
    }
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

}
