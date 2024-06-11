package io.bootify.my_app.controller;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.repos.ProprtyWonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/propertyOwners")
public class PropertyOwnerController {


    @Autowired
    private ProprtyWonerRepository propertyOwnerRepository;

    @GetMapping
    public List<PropertyOwner> getAllPropertyOwners() {
        return propertyOwnerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyOwner> getPropertyOwnerById(@PathVariable Integer id) {
        Optional<PropertyOwner> propertyOwner = propertyOwnerRepository.findById(id);
        return propertyOwner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public PropertyOwner createPropertyOwner(@RequestBody PropertyOwner propertyOwner) {
        return propertyOwnerRepository.save(propertyOwner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyOwner> updatePropertyOwner(@PathVariable Integer id, @RequestBody PropertyOwner propertyOwnerDetails) {
        Optional<PropertyOwner> optionalPropertyOwner = propertyOwnerRepository.findById(id);
        if (optionalPropertyOwner.isPresent()) {
            PropertyOwner propertyOwner = optionalPropertyOwner.get();
            propertyOwner.setMoNumber(propertyOwnerDetails.getMoNumber());
            propertyOwner.setAddress(propertyOwnerDetails.getAddress());
            propertyOwner.setUserUser(propertyOwnerDetails.getUserUser());
            // Update other fields as needed

            PropertyOwner updatedPropertyOwner = propertyOwnerRepository.save(propertyOwner);
            return ResponseEntity.ok(updatedPropertyOwner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropertyOwner(@PathVariable Integer id) {
        if (propertyOwnerRepository.existsById(id)) {
            propertyOwnerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
