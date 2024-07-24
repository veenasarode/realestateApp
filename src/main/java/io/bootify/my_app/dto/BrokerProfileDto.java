package io.bootify.my_app.dto;


import io.bootify.my_app.domain.BrokerProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BrokerProfileDto {
    private Integer brokerProfileId;
    private String name;
    private String docNumber;
    private String fullAddress;
    private String city;
    private Integer userId;

    public BrokerProfileDto(Integer brokerProfileId, String name, String docNumber, String fullAddress, String city) {
        this.brokerProfileId = brokerProfileId;
        this.name = name;
        this.docNumber = docNumber;
        this.fullAddress = fullAddress;
        this.city = city;
    }

    public BrokerProfileDto(BrokerProfile brokerProfile){
        this.brokerProfileId = brokerProfile.getBrokerProfileId();
        this.name = brokerProfile.getName();
        this.docNumber = brokerProfile.getDocNumber();
        this.fullAddress = brokerProfile.getFullAddress();
        this.city = brokerProfile.getCity();
       // this.userId  = brokerProfile.getUser().getUserId();

        /*if(brokerProfile.getUser() != null)
        {
            this.userId  = brokerProfile.getUser().getUserId();
        }*/
    }
}
