package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.PropertyOwnerDto;
import io.bootify.my_app.repos.ProprtyWonerRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.PropertyOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PropertyOwnerServiceImpl implements PropertyOwnerService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProprtyWonerRepository propertyOwnerRepository;
    @Override
    public List<PropertyOwner> getAllPropertyOwners() {
        try {
            return propertyOwnerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving property owners: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<PropertyOwner> getPropertyOwnerById(Integer id) {
        try {
            return propertyOwnerRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving property owner by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public PropertyOwner createPropertyOwner(PropertyOwnerDto propertyOwnerDto) {
        try {

            User user = userRepo.findById(propertyOwnerDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            PropertyOwner propertyOwner = new PropertyOwner(propertyOwnerDto);

            propertyOwner.setUserUser(user);

            return propertyOwnerRepository.save(propertyOwner);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error creating property owner: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error creating property owner", e);
        }
    }


    @Override
    public PropertyOwner updatePropertyOwner(Integer id, PropertyOwnerDto propertyOwnerDto) {
        try {
            Optional<PropertyOwner> optionalPropertyOwner = propertyOwnerRepository.findById(id);
            if (optionalPropertyOwner.isPresent()) {
                PropertyOwner propertyOwner = optionalPropertyOwner.get();
                propertyOwner.setMoNumber(propertyOwnerDto.getMoNumber());
                propertyOwner.setAddress(propertyOwnerDto.getAddress());

                return propertyOwnerRepository.save(propertyOwner);
            } else {
                throw new RuntimeException("Property owner not found with ID: " + id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while updating property owner: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while updating property owner: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePropertyOwner(Integer id) {
        try {
            if (propertyOwnerRepository.existsById(id)) {
                propertyOwnerRepository.deleteById(id);
            } else {
                throw new RuntimeException("Property owner not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting property owner: " + e.getMessage(), e);
        }
    }
}
