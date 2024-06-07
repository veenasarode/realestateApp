package io.bootify.my_app.dto;


import io.bootify.my_app.domain.Property;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    private Integer propertyId;

    private String name;

    private String address;


    private String type;


    private String status;


    private String area;


    private String city;


    private String comments;


    private String flore;

    public PropertyDto(Property property){
        this.propertyId = property.getPropertyId();
        this.name = property.getName();
        this.address = property.getAddress();
        this.type = property.getType();
        this.status = property.getStatus();
        this.area = property.getArea();
        this.city = property.getCity();
        this.comments = property.getComments();
        this.flore = property.getFlore();
    }
}
