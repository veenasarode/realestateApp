package io.bootify.my_app.service;


import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeaseService {
    LeaseDto addLease(LeaseDto leaseDto);

    LeaseDto updateLease(LeaseDto leaseDto, Integer leaseId);

    List<LeaseDto> getAllLeases(int pageNo, int pageSize) throws UserNotFound;

    LeaseDto getLeaseById(Integer leaseId) throws LeaseNotFoundException;

    List<LeaseDto> getLeasesByPropertyOwnerId(Integer proprtyWonerId);

    List<LeaseDto> getLeasesByBrokerProfileId(Integer brokerProfileId);


    void deleteLeaseById(Integer leaseId) throws LeaseNotFoundException;

    List<LeaseDto> getLeasesByUserId(Integer userId);


}
