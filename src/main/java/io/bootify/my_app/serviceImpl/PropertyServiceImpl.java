package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.repos.ProprtyWonerRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ProprtyWonerRepository proprtyWonerRepository;
    @Autowired
    private UserRepo userRepo;
    @Override
    public String addProperty(PropertyDto propertyDto) {
        try {
            User user = userRepo.findById(propertyDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            PropertyOwner propertyOwner = proprtyWonerRepository.findById(propertyDto.getPropertyOwnerId())
                    .orElseThrow(() -> new RuntimeException("Property owner not found"));
            Property property=new Property(propertyDto);
           /* this.userRepo.save(user);
            this.propertyRepository.save(property);
            this.proprtyWonerRepository.save(propertyOwner);*/
            property.setUserUser(user);
            property.setProprtyWoner(propertyOwner);
            propertyRepository.save(property);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String updateProperty(PropertyDto propertyDto,Integer propertyId) {
        try {
            Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
            if (optionalProperty.isPresent()) {
                Property property = optionalProperty.get();

                property.setArea(propertyDto.getArea());
                property.setName(propertyDto.getName());
                property.setType(propertyDto.getType());
                property.setAddress(propertyDto.getAddress());
                propertyRepository.save(property);

                return "success";
            } else {
                throw new PropertyNotFoundException("Property not found with ID: " + propertyDto.getPropertyId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (PropertyNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PropertyDto> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream()
                .map(PropertyDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDto getPropertyById(Integer propertyId) throws PropertyNotFoundException {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isPresent()) {
            return new PropertyDto(optionalProperty.get());
        } else {
            throw new PropertyNotFoundException("Property not found with ID: " + propertyId);
        }
    }

    @Override
    public void deletePropertyById(Integer propertyId) throws PropertyNotFoundException {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isPresent()) {
          this.propertyRepository.deleteById(propertyId);
        } else {
            throw new PropertyNotFoundException("Property not found with ID: " + propertyId);
        }
    }

}
