package com.scholarship.controller;

import com.scholarship.entity.User;
import com.scholarship.service.UserService;
import com.scholarship.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Result<User>> login(@RequestBody Map<String, String> requestData) {
        String username = requestData.get("username");
        String password = requestData.get("password");

        User user = userService.findUser(username, password);
        System.out.println(user);
        if (user == null) {
            Result<User> result = new Result<>(201, "登录失败，用户名或密码不正确", null);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            Result<User> result = new Result<>(200, "登录成功!", user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
