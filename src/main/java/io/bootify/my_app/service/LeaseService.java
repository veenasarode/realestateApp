package io.bootify.my_app.service;


import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.LeaseNotFoundException;

import java.util.List;

public interface LeaseService {
    String addLease(LeaseDto leaseDto);

    String updateLease(LeaseDto leaseDto, Integer leaseId);

    List<LeaseDto> getAllLeases();

    LeaseDto getLeaseById(Integer leaseId) throws LeaseNotFoundException;

    void deleteLeaseById(Integer leaseId) throws LeaseNotFoundException;
}
