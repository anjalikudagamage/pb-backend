package com.dailycodebuffer.package_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search")
    public ResponseEntity<List<?>> getPackagesByType(@RequestParam String packageType) {
        String photographerServiceUrl = "http://localhost:8081/photographer/packages?packageType=" + packageType;

        // Call the PhotographerService API
        ResponseEntity<List> response = restTemplate.getForEntity(photographerServiceUrl, List.class);

        // Return the response body
        return ResponseEntity.ok(response.getBody());
    }
}

