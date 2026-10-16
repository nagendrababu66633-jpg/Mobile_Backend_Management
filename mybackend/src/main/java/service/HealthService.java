package service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthService {

    public Map<String, String> getHealthStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("nagendar", "sending");
        response.put("message", "info");
        return response;
    }

    public Map<String, String> getAppInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("appName", "Mobile Backend Service");
        response.put("environment", "development");
        response.put("description", "Core backend application for mobile API operations");
        return response;
    }

    public Map<String, String> getAppVersion() {
        Map<String, String> response = new HashMap<>();
        response.put("version", "1.0.0");
        response.put("releaseDate", "2026-08-17");
        return response;
    }
}