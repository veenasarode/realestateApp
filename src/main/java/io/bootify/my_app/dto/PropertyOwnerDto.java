package io.bootify.my_app.dto;

import io.bootify.my_app.domain.PropertyOwner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PropertyOwnerDto {
    private Integer propertyOwnerId;
    private String moNumber;
    private String address;
    private String status;
    private Integer userId;
    private Set<Integer> propertyIds;
    private Set<Integer> leaseIds;


    public PropertyOwnerDto(PropertyOwner propertyOwner) {
        this.propertyOwnerId = propertyOwner.getProprtyWonerId();
        this.moNumber = propertyOwner.getMoNumber();
        this.address = propertyOwner.getAddress();
        this.status=propertyOwner.getStatus();
    }

    public PropertyOwnerDto(Integer propertyOwnerId, String moNumber, String address) {
        this.propertyOwnerId = propertyOwnerId;
        this.moNumber = moNumber;
        this.address = address;
    }
}
