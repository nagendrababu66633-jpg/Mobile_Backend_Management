package controller;

import service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {

        this.healthService = healthService;
    }

    @GetMapping("/health")
    public Map<String, String> getHealth() {

        return healthService.getHealthStatus();
    }
    @GetMapping("/info")
    public Map<String, String> getInfo() {

        return healthService.getAppInfo();


    }

    @GetMapping("/version")
    public Map<String, String> getVersion() {

        return healthService.getAppVersion();
    }
}