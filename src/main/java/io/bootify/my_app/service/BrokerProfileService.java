package io.bootify.my_app.service;



import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrokerProfileService {
    BrokerProfileDto addBrokerProfile(BrokerProfileDto brokerProfileDto);

    BrokerProfileDto updateBrokerProfile(BrokerProfileDto brokerProfileDto, Integer brokerProfileId);

    List<BrokerProfileDto> getAllBrokerProfiles();

    public Page<BrokerProfile> findBrokerProfileWithPaginationAndSorting(int offset , int pageSize , String field);

    BrokerProfileDto getBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException;

    void deleteBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException;

    List<BrokerProfileDto> getBrokerByUserId(Integer userId) throws BrokerProfileNotFoundException;
}
