package com.scholarship.controller;

import com.scholarship.entity.ScholarshipApplication;
import com.scholarship.service.ScholarshipApplicationService;
import com.scholarship.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/applications")
public class ScholarshipApplicationController {
    private final ScholarshipApplicationService applicationService;

    @Autowired
    public ScholarshipApplicationController(ScholarshipApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Result<String>> submitApplication(@Validated @RequestBody ScholarshipApplication application, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            Result<String> result = new Result<>(400, errorMessage, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        applicationService.submitScholarshipApplication(application);
        Result<String> result = new Result<>(200, "提交成功", null);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}