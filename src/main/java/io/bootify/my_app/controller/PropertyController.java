package io.bootify.my_app.controller;

import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {


    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<?> createProperty(@RequestBody PropertyDto propertyDto){
        this.propertyService.addProperty(propertyDto);
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/getById")
    public ResponseEntity<?> getpropertyById(@RequestParam Integer propertyId) throws PropertyNotFoundException {
        try {
            PropertyDto propertyById = this.propertyService.getPropertyById(propertyId);
            return new ResponseEntity<>(propertyId, HttpStatus.OK);
        } catch (PropertyNotFoundException e) {
            throw new PropertyNotFoundException("Broker profile not found with ID: " + propertyId);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PropertyDto>> showAllProperty() {
        List<PropertyDto> allProperties = this.propertyService.getAllProperties();
        return new ResponseEntity<>(allProperties, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> editProperty(@RequestBody PropertyDto propertyDto, @RequestParam Integer propertyId) {
        try {
            this.propertyService.updateProperty(propertyDto, propertyId);
            return ResponseEntity.ok("Broker profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update broker profile: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deletePropertyById(@RequestParam Integer propertyId) throws PropertyNotFoundException {
        try {
            this.propertyService.deletePropertyById(propertyId);
            return ResponseEntity.ok(" Property deleted successfully.");
        } catch (PropertyNotFoundException e) {
            throw new PropertyNotFoundException("Property not found with ID: " + propertyId);
        }
    }
}