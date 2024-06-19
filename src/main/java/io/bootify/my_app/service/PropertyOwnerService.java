package io.bootify.my_app.service;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.dto.PropertyOwnerDto;

import java.util.List;
import java.util.Optional;

public interface PropertyOwnerService {
    List<PropertyOwner> getAllPropertyOwners();
    Optional<PropertyOwner> getPropertyOwnerById(Integer id);
    PropertyOwner createPropertyOwner(PropertyOwnerDto propertyOwnerDto);
    PropertyOwner updatePropertyOwner(Integer id, PropertyOwnerDto propertyOwnerDto);
    void deletePropertyOwner(Integer id);
}
