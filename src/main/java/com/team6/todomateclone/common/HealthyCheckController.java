package com.team6.todomateclone.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HealthyCheckController {

    @GetMapping
    public ResponseEntity healthyCheck() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
