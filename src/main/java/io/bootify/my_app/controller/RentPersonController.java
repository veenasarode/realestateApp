package io.bootify.my_app.controller;


import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.service.RentPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentPersons")
public class RentPersonController {

    @Autowired
    private RentPersonService rentPersonService;

    @GetMapping
    public List<RentPersonDto> getAllRentPersons() {
        return rentPersonService.getAllRentPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentPersonDto> getRentPersonById(@PathVariable Integer id) {
        Optional<RentPersonDto> rentPersonDTO = rentPersonService.getRentPersonById(id);
        return rentPersonDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public RentPersonDto createRentPerson(@RequestBody RentPersonDto rentPersonDTO) {
        return rentPersonService.createRentPerson(rentPersonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentPersonDto> updateRentPerson(@PathVariable Integer id, @RequestBody RentPersonDto rentPersonDTO) {
        RentPersonDto updatedRentPersonDTO = rentPersonService.updateRentPerson(id, rentPersonDTO);
        if (updatedRentPersonDTO != null) {
            return ResponseEntity.ok(updatedRentPersonDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentPerson(@PathVariable Integer id) {
        rentPersonService.deleteRentPerson(id);
        return ResponseEntity.noContent().build();
    }
}