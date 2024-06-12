package io.bootify.my_app.service;

import io.bootify.my_app.domain.PropertyOwner;

import java.util.List;
import java.util.Optional;

public interface PropertyOwnerService {
    List<PropertyOwner> getAllPropertyOwners();
    Optional<PropertyOwner> getPropertyOwnerById(Integer id);
    PropertyOwner createPropertyOwner(PropertyOwner propertyOwner);
    PropertyOwner updatePropertyOwner(Integer id, PropertyOwner propertyOwnerDetails);
    void deletePropertyOwner(Integer id);
}
