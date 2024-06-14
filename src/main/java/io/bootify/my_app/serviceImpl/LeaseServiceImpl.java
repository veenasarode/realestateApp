package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.*;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.repos.*;
import io.bootify.my_app.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ProprtyWonerRepository proprtyWonerRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BrokerProfileRepository brokerProfileRepository;

    @Autowired
    private RentPersonRepository rentPersonRepository;


    @Override
    public String addLease(LeaseDto leaseDto) {
        try {
            Property property = propertyRepository.findById(leaseDto.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found"));
            User user = userRepo.findById(leaseDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            BrokerProfile brokerProfile = brokerProfileRepository.findById(leaseDto.getBrokerProfileId())
                    .orElseThrow(() -> new RuntimeException("Broker Not Found"));
            PropertyOwner propertyOwner = proprtyWonerRepository.findById(leaseDto.getPropertyOwnerId())
                    .orElseThrow(() -> new RuntimeException("Property owner not found"));
           /* RentPerson rentPerson = rentPersonRepository.findById(leaseDto.getRentPerson())
                    .orElseThrow(() -> new RuntimeException("Rent Person Not Found"));*/

            Lease lease = new Lease(leaseDto);
            lease.setPropertyProperty(property);
            lease.setUserUser(user);
            lease.setBrokerProfiles(brokerProfile);
            lease.setProprtyWoner(propertyOwner);
            //lease.setRentPerson(rentPerson);
            leaseRepository.save(lease);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateLease(LeaseDto leaseDto, Integer leaseId) {
        try {
            Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
            if (optionalLease.isPresent()) {
                Lease lease = optionalLease.get();
                lease.setStartDate(leaseDto.getStartDate());
                lease.setEndDate(leaseDto.getEndDate());
                lease.setStatus(leaseDto.getStatus());
                leaseRepository.save(lease);
                return "success";
            } else {
                throw new LeaseNotFoundException("Lease not found with ID: " + leaseDto.getLeaseId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LeaseDto> getAllLeases() {
        List<Lease> leases = leaseRepository.findAll();
        return leases.stream()
                .map(LeaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public LeaseDto getLeaseById(Integer leaseId) throws LeaseNotFoundException {
        Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
        if (optionalLease.isPresent()) {
            return new LeaseDto(optionalLease.get());
        } else {
            throw new LeaseNotFoundException("Lease not found with ID: " + leaseId);
        }
    }

    @Override
    public void deleteLeaseById(Integer leaseId) throws LeaseNotFoundException {
        Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
        if (optionalLease.isPresent()) {
            this.leaseRepository.deleteById(leaseId);
        } else {
            throw new LeaseNotFoundException("Lease not found with ID: " + leaseId);
        }
    }
}
