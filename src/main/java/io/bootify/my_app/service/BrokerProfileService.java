package io.bootify.my_app.service;



import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrokerProfileService {
    void addBrokerProfile(BrokerProfileDto brokerProfileDto);

    void updateBrokerProfile(BrokerProfileDto brokerProfileDto, Integer brokerProfileId);

    List<BrokerProfileDto> getAllBrokerProfiles();

    public Page<BrokerProfile> findBrokerWithPaginationAndSorting(int offset , int pageSize , String field);

    BrokerProfileDto getBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException;

    void deleteBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException;

    List<BrokerProfileDto> getBrokerByUserId(Integer userId) throws ResourceNotFoundException;
}
