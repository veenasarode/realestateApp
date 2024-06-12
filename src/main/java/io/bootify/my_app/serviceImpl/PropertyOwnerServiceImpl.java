package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.repos.ProprtyWonerRepository;
import io.bootify.my_app.service.PropertyOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PropertyOwnerServiceImpl implements PropertyOwnerService {
    @Autowired
    private ProprtyWonerRepository propertyOwnerRepository;

    @Override
    public List<PropertyOwner> getAllPropertyOwners() {
        return propertyOwnerRepository.findAll();
    }

    @Override
    public Optional<PropertyOwner> getPropertyOwnerById(Integer id) {
        return propertyOwnerRepository.findById(id);
    }

    @Override
    public PropertyOwner createPropertyOwner(PropertyOwner propertyOwner) {
        return propertyOwnerRepository.save(propertyOwner);
    }

    @Override
    public PropertyOwner updatePropertyOwner(Integer id, PropertyOwner propertyOwnerDetails) {
        Optional<PropertyOwner> optionalPropertyOwner = propertyOwnerRepository.findById(id);
        if (optionalPropertyOwner.isPresent()) {
            PropertyOwner propertyOwner = optionalPropertyOwner.get();
            propertyOwner.setMoNumber(propertyOwnerDetails.getMoNumber());
            propertyOwner.setAddress(propertyOwnerDetails.getAddress());
            propertyOwner.setUserUser(propertyOwnerDetails.getUserUser());
            // Update other fields as needed

            return propertyOwnerRepository.save(propertyOwner);
        } else {
            return null; // Or throw an exception
        }
    }

    @Override
    public void deletePropertyOwner(Integer id) {
        if (propertyOwnerRepository.existsById(id)) {
            propertyOwnerRepository.deleteById(id);
        }
    }

}
