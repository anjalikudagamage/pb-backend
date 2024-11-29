package com.dailycodebuffer.photographer_service.repository;

import com.dailycodebuffer.photographer_service.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
    Optional<Photographer> findByEmailAndPassword(String email, String password);
    Optional<Photographer> findById(Long id);
}

