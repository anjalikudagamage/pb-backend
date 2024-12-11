package com.dailycodebuffer.photographer_service.controller;

import com.dailycodebuffer.photographer_service.model.Photographer;
import com.dailycodebuffer.photographer_service.repository.PhotographerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/photographer")
public class PhotographerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotographerController.class);

    @Autowired
    private PhotographerRepository repository;

    @PostMapping("/signup")
    public ResponseEntity<Photographer> signup(@RequestBody Photographer photographer) {
        LOGGER.info("Photographer signup: {}", photographer);
        Photographer savedPhotographer = repository.save(photographer);
        return ResponseEntity.ok(savedPhotographer);
    }

    @PostMapping("/login")
    public ResponseEntity<Photographer> login(@RequestBody Map<String, String> loginDetails) {
        String email = loginDetails.get("email");
        String password = loginDetails.get("password");

        Optional<Photographer> photographer = repository.findByEmailAndPassword(email, password);

        if (photographer.isPresent()) {
            LOGGER.info("Login successful for: {}", email);
            return ResponseEntity.ok(photographer.get());
        } else {
            LOGGER.warn("Login failed for: {}", email);
            return ResponseEntity.status(401).body(null);
        }
    }


    @GetMapping("/{id}/details")
    public ResponseEntity<Photographer> getPhotographerDetails(@PathVariable Long id) {
        LOGGER.info("Fetching photographer details for id={}", id);
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Photographer>> findAll() {
        LOGGER.info("Photographer find all");
        List<Photographer> photographers = repository.findAll();
        return ResponseEntity.ok(photographers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photographer> findById(@PathVariable Long id) {
        LOGGER.info("Photographer find: id={}", id);
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/allData")
    public ResponseEntity<List<Map<String, Object>>> getAllPhotographers() {
        LOGGER.info("Fetching photographer data with package details");

        List<Photographer> photographers = repository.findAll();
        List<Map<String, Object>> responseData = photographers.stream().map(photographer -> {
            // Extract relevant data
            Map<String, Object> photographerData = Map.of(
                    "businessName", photographer.getBusinessName(),
                    "packageDetails", photographer.getPackageDetails()
            );
            return photographerData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Photographer> updatePhotographer(
            @PathVariable Long id,
            @RequestBody Photographer updatedPhotographer) {
        Optional<Photographer> existingPhotographer = repository.findById(id);
        if (existingPhotographer.isPresent()) {
            Photographer photographer = existingPhotographer.get();
            // Update fields
            photographer.setBusinessName(updatedPhotographer.getBusinessName());
            photographer.setBusinessDescription(updatedPhotographer.getBusinessDescription());
            photographer.setEmail(updatedPhotographer.getEmail());
            photographer.setPassword(updatedPhotographer.getPassword());
            // Handle packageDetails conversion
            Map<String, String> convertedPackageDetails = new HashMap<>();
            for (Map.Entry<String, String> entry : updatedPhotographer.getPackageDetails().entrySet()) {
                String detailsString = entry.getValue();
                convertedPackageDetails.put(entry.getKey(), detailsString);
            }
            photographer.setPackageDetails(convertedPackageDetails);
            Photographer savedPhotographer = repository.save(photographer);
            LOGGER.info("Photographer updated successfully with id={}", id);
            return ResponseEntity.ok(savedPhotographer);
        } else {
            LOGGER.warn("Photographer with id={} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}


