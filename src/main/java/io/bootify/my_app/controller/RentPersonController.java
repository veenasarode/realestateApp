package io.bootify.my_app.controller;


import io.bootify.my_app.dto.*;
import io.bootify.my_app.exception.AgreementNotFoundException;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.repos.RentPersonRepository;
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

    @Autowired
    private RentPersonRepository rentPersonRepository;
    @GetMapping("/{id}")
    public ResponseEntity<RentPersonDto> getRentPersonById(@PathVariable Integer id) {
        try {
            RentPersonDto rentPersonDto = this.rentPersonService.getRentPersonById(id);
            return new ResponseEntity<>(rentPersonDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new AgreementNotFoundException("Rent Person not found with ID: " + id);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<RentPersonDto> createRentPerson(@RequestBody RentPersonDto rentPersonDTO) {
        try {
            RentPersonDto createdRentPerson = rentPersonService.createRentPerson(rentPersonDTO);

            ResponseEntity<RentPersonDto> responseEntity = new ResponseEntity<>(createdRentPerson , HttpStatus.CREATED);

            return responseEntity;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create RentPerson" + e.getMessage()) ;
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

    @GetMapping("/getAllRentPersons")
    public ResponseEntity<ResponseRentPersonDto> getAllRentPersons(@RequestParam int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<RentPersonDto> listOfRentPersons = this.rentPersonService.getAllRentPersons(pageNo, pageSize);
            int totalPages = getTotalPagesForRentPersons(pageSize);
            ResponseRentPersonDto responseRentPersonDto = new ResponseRentPersonDto("success", listOfRentPersons, totalPages);
            return ResponseEntity.status(HttpStatus.OK).body(responseRentPersonDto);
        } catch (ResourceNotFoundException | UserNotFound rentPersonNotFoundException) {
            ResponseRentPersonDto responseRentPersonDto = new ResponseRentPersonDto("unsuccess");
            responseRentPersonDto.setException("Rent person not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseRentPersonDto);
        } catch (PageNotFoundException pageNotFoundException) {
            ResponseRentPersonDto responseRentPersonDto = new ResponseRentPersonDto("unsuccess");
            responseRentPersonDto.setException("Page not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseRentPersonDto);
        }
    }

    private int getTotalPagesForRentPersons(int pageSize) {
        int totalRentPersons = rentPersonRepository.findAll().size();
        return (int) Math.ceil((double) totalRentPersons / pageSize);
    }


}