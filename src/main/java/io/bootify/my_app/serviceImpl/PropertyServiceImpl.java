package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Property;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.repos.PropertyRepository;
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

    @Override
    public String addProperty(PropertyDto propertyDto) {
        try {
            Property property=new Property(propertyDto);
            this.propertyRepository.save(property);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String updateProperty(PropertyDto propertyDto,Integer propertyId) {
        try {
            // Retrieve the property from the database based on its ID
            Optional<Property> optionalProperty = propertyRepository.findById(propertyId);

            // Check if the property exists
            if (optionalProperty.isPresent()) {
                // Get the property object from Optional
                Property property = optionalProperty.get();

                // Update the fields of the property with values from the provided PropertyDto
                property.setArea(propertyDto.getArea());
              //  property.setPrice(propertyDto.getPrice());
                property.setName(propertyDto.getName());
                property.setType(propertyDto.getType());

                // Save the updated property back to the database
                propertyRepository.save(property);

                return "success";
            } else {
                // If property with the provided ID is not found
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
                .map(PropertyDto::new) // Using constructor reference
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
