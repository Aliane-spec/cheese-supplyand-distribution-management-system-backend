package com.example.cheesesupply.controller;
import com.example.cheesesupply.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardServe;
    @GetMapping(value = "/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return new ResponseEntity<>(dashboardServe.getSummary(), HttpStatus.OK);
    }
}
