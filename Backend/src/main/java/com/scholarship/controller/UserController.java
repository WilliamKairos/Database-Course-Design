package com.scholarship.controller;
import com.scholarship.entity.User;
import com.scholarship.service.UserService;
import com.scholarship.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Result<User>> login(@RequestParam("username") String username,
                                              @RequestParam("password") String password) {
        Result<User> result = userService.findUserByUsernameAndPassword(username, password);
        return ResponseEntity.status(result.getCode()).body(result);
    }
}
