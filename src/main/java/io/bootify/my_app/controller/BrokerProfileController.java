package io.bootify.my_app.controller;


import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.service.BrokerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/broker-profile")
public class BrokerProfileController {

    @Autowired
    private BrokerProfileService brokerProfileService;

    @PostMapping("/add")
    public ResponseEntity<BrokerProfileDto> createBrokerProfile(@RequestBody BrokerProfileDto brokerProfileDto) {
        try {

           brokerProfileDto = brokerProfileService.addBrokerProfile(brokerProfileDto);

            ResponseEntity<BrokerProfileDto> responseEntity = new ResponseEntity<>(brokerProfileDto , HttpStatus.CREATED);

            return responseEntity;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Brokerprofile" + e.getMessage()) ;
        }
    }

    @PutMapping("/update")
    public ResponseEntity<BrokerProfileDto> editBrokerProfile(@RequestBody BrokerProfileDto brokerProfileDto, @RequestParam Integer brokerProfileId) {
        try {
            BrokerProfileDto updatedBroker = brokerProfileService.updateBrokerProfile(brokerProfileDto, brokerProfileId);

            ResponseEntity<BrokerProfileDto> responseEntity = new ResponseEntity<>(updatedBroker , HttpStatus.OK);

            return responseEntity;

        } catch (Exception e) {
            throw new RuntimeException("Failed to update Brokerprofile" + e.getMessage()) ;
        }
    }

    /*@GetMapping("/getAll")
    public ResponseEntity<List<BrokerProfileDto>> showAllBrokerProfiles() {
        List<BrokerProfileDto> allBrokerProfiles = this.brokerProfileService.getAllBrokerProfiles();
        return new ResponseEntity<>(allBrokerProfiles, HttpStatus.OK);
    }*/

    @GetMapping("/getAll/{offset}/{pageSize}/{field}")
    public ResponseEntity<Object> showAllBrokerProfile(@PathVariable int offset , @PathVariable int pageSize , @PathVariable String field) {
        Page<BrokerProfile> allBrokers = this.brokerProfileService.findBrokerProfileWithPaginationAndSorting(offset, pageSize, field);
        return new ResponseEntity<>(allBrokers, HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<?> showBrokerProfileById(@RequestParam Integer brokerProfileId) throws BrokerProfileNotFoundException {
        try {
            BrokerProfileDto brokerProfileById = this.brokerProfileService.getBrokerProfileById(brokerProfileId);
            return new ResponseEntity<>(brokerProfileById, HttpStatus.OK);
        } catch (BrokerProfileNotFoundException e) {
            throw new BrokerProfileNotFoundException("Broker profile not found with ID: " + brokerProfileId);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteBrokerProfileById(@RequestParam Integer brokerProfileId) throws BrokerProfileNotFoundException {
        try {
            this.brokerProfileService.deleteBrokerProfileById(brokerProfileId);
            return ResponseEntity.ok("Broker profile deleted successfully.");
        } catch (BrokerProfileNotFoundException e) {
            throw new BrokerProfileNotFoundException("Broker profile not found with ID: " + brokerProfileId);
        }
    }

    @GetMapping("/getByUserId")
    public ResponseEntity<List<BrokerProfileDto>> getBrokerProfileByUserId(@RequestParam Integer userId) throws BrokerProfileNotFoundException {
        try {
            List<BrokerProfileDto> brokers = this.brokerProfileService.getBrokerByUserId(userId);
            return new ResponseEntity<List<BrokerProfileDto>>(brokers, HttpStatus.OK);
        } catch (BrokerProfileNotFoundException e) {
            throw new BrokerProfileNotFoundException("Broker profile not found with ID: " + userId);
        }
    }
}
