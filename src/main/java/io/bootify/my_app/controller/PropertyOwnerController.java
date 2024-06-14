package io.bootify.my_app.controller;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.service.PropertyOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/propertyOwners")
public class PropertyOwnerController {


    @Autowired
    private PropertyOwnerService propertyOwnerService;



    @PostMapping("/")
    public PropertyOwner createPropertyOwner(@RequestBody PropertyOwner propertyOwner) {
        return propertyOwnerService.createPropertyOwner(propertyOwner);
    }

    @PutMapping("/{id}")
    public PropertyOwner updatePropertyOwner(@PathVariable Integer id, @RequestBody PropertyOwner propertyOwnerDetails) {
        return propertyOwnerService.updatePropertyOwner(id, propertyOwnerDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePropertyOwner(@PathVariable Integer id) {
        propertyOwnerService.deletePropertyOwner(id);
    }
}
