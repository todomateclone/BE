package com.team6.todomateclone.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthyCheckController {

    @GetMapping(value = "/healthcheck")
    public HealthStatus healthcheck() {
        return new HealthStatus("ok");
    }

    class HealthStatus {
        private String status;

        HealthStatus(String status) {
            this.status = status;
        }

        String getStatus() {
            return status;
        }
    }
}