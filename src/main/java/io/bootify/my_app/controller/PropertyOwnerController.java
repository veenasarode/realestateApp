package io.bootify.my_app.controller;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.dto.PropertyOwnerDto;
import io.bootify.my_app.service.PropertyOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/propertyOwners")
public class PropertyOwnerController {


    @Autowired
    private PropertyOwnerService propertyOwnerService;




    @PostMapping("/")
    public ResponseEntity<?> createPropertyOwner(@RequestBody PropertyOwnerDto propertyOwnerDto) {
        try {
            PropertyOwner propertyOwner = propertyOwnerService.createPropertyOwner(propertyOwnerDto);
            return ResponseEntity.ok("Property Owner added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add property owner: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePropertyOwner(@PathVariable Integer id, @RequestBody PropertyOwnerDto propertyOwnerDetails) {
        try {
            PropertyOwner updatedPropertyOwner = propertyOwnerService.updatePropertyOwner(id, propertyOwnerDetails);
            return ResponseEntity.ok("Property Owner updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update property owner: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropertyOwner(@PathVariable Integer id) {
        try {
            propertyOwnerService.deletePropertyOwner(id);
            return ResponseEntity.ok("Property Owner deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete property owner: " + e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllPropertyOwners() {
        try {
            List<PropertyOwner> propertyOwnerList = propertyOwnerService.getAllPropertyOwners();
            return ResponseEntity.ok(propertyOwnerList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get property owners: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyOwnerById(@PathVariable Integer id) {
        try {
            Optional<PropertyOwner> propertyOwner = propertyOwnerService.getPropertyOwnerById(id);
            return new ResponseEntity<>("Succesfull: " , HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to get property owner: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
