package com.dailycodebuffer.booking_service.controller;

import com.dailycodebuffer.booking_service.model.Booking;
import com.dailycodebuffer.booking_service.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingRepository repository;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        LOGGER.info("Creating booking: {}", booking);
        Booking savedBooking = repository.save(booking);
        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        LOGGER.info("Fetching booking details for id={}", id);
        Optional<Booking> booking = repository.findById(id);
        return booking.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/alldata")
    public ResponseEntity<List<Booking>> getAllBookings() {
        LOGGER.info("Fetching all bookings");
        List<Booking> bookings = repository.findAll();
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        LOGGER.info("Deleting booking with id={}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        LOGGER.info("Updating booking with id={}", id);
        return repository.findById(id).map(existingBooking -> {
            existingBooking.setFullName(updatedBooking.getFullName());
            existingBooking.setEmail(updatedBooking.getEmail());
            existingBooking.setPackageName(updatedBooking.getPackageName());
            existingBooking.setEventDate(updatedBooking.getEventDate());
            existingBooking.setEventTime(updatedBooking.getEventTime());
            existingBooking.setAddress(updatedBooking.getAddress());
            existingBooking.setPhoneNumber(updatedBooking.getPhoneNumber());
            Booking savedBooking = repository.save(existingBooking);
            return ResponseEntity.ok(savedBooking);
        }).orElse(ResponseEntity.notFound().build());
    }

}

