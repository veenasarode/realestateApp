package io.bootify.my_app.controller;


import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.service.RentPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentPersons")
public class RentPersonController {

    @Autowired
    private RentPersonService rentPersonService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentPersonById(@PathVariable Integer id) {
        try {
            Optional<RentPersonDto> rentPersonById = rentPersonService.getRentPersonById(id);
            return new ResponseEntity<>(rentPersonById, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to get rent person: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> createRentPerson(@RequestBody RentPersonDto rentPersonDTO) {
        try {
            RentPersonDto createdRentPerson = rentPersonService.createRentPerson(rentPersonDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdRentPerson);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create rent person: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRentPerson(@PathVariable Integer id, @RequestBody RentPersonDto rentPersonDTO) {
        try {
            RentPersonDto updatedRentPersonDTO = rentPersonService.updateRentPerson(id, rentPersonDTO);
            if (updatedRentPersonDTO != null) {
                return ResponseEntity.ok(updatedRentPersonDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Rent Person not found with id: " + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update rent person: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRentPerson(@PathVariable Integer id) {
        try {
            rentPersonService.deleteRentPerson(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete rent person: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRentPersons() {
        try {
            List<RentPersonDto> rentPersonList = rentPersonService.getAllRentPersons();
            return ResponseEntity.ok(rentPersonList); // Return 200 OK with rent person list
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get rent persons: " + e.getMessage()); // Return 500 Internal Server Error with error message
        }
    }

}