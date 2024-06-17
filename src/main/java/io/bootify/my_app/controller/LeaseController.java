package io.bootify.my_app.controller;

import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lease")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @PostMapping("/add")
    public ResponseEntity<?> createLease(@RequestBody LeaseDto leaseDto){
        this.leaseService.addLease(leaseDto);
        return ResponseEntity.ok("Lease added successfully.");
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getLeaseById(@RequestParam Integer leaseId) throws LeaseNotFoundException {
        try {
            LeaseDto leaseById = this.leaseService.getLeaseById(leaseId);
            return new ResponseEntity<>(leaseById, HttpStatus.OK);
        } catch (LeaseNotFoundException e) {
            throw new LeaseNotFoundException("Lease not found with ID: " + leaseId);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LeaseDto>> showAllLeases() {
        List<LeaseDto> allLeases = this.leaseService.getAllLeases();
        return new ResponseEntity<>(allLeases, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> editLease(@RequestBody LeaseDto leaseDto, @RequestParam Integer leaseId) {
        try {
            this.leaseService.updateLease(leaseDto, leaseId);
            return ResponseEntity.ok("Lease updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update lease: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteLeaseById(@RequestParam Integer leaseId) throws LeaseNotFoundException {
        try {
            this.leaseService.deleteLeaseById(leaseId);
            return ResponseEntity.ok("Lease deleted successfully.");
        } catch (LeaseNotFoundException e) {
            throw new LeaseNotFoundException("Lease not found with ID: " + leaseId);
        }
    }
}
