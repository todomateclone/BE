package com.team6.todomateclone.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HealthyCheckController {

    @GetMapping(value = "/")
    public HealthStatus healthcheck() {
        return new HealthStatus("ok");
    }

    class HealthStatus {
        private String status;

        HealthStatus(String status) {
            this.status = status;
        }
    }
}