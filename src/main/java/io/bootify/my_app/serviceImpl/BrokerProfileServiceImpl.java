package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.repos.BrokerProfileRepository;
import io.bootify.my_app.service.BrokerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrokerProfileServiceImpl implements BrokerProfileService {

    @Autowired
    private BrokerProfileRepository brokerProfileRepository;

    public BrokerProfileDto addBrokerProfile(BrokerProfileDto brokerProfileDto) {
        try {

            BrokerProfile brokerProfile = new BrokerProfile(brokerProfileDto);

           BrokerProfile saveBrokerProfile = brokerProfileRepository.save(brokerProfile);

           BrokerProfileDto saveDto = new BrokerProfileDto(saveBrokerProfile);

           return saveDto;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
@Override
    public BrokerProfileDto updateBrokerProfile(BrokerProfileDto brokerProfileDto, Integer brokerProfileId) {
        try {
            Optional<BrokerProfile> optionalBrokerProfile = brokerProfileRepository.findById(brokerProfileId);

            if (optionalBrokerProfile.isPresent()) {
                BrokerProfile brokerProfile = optionalBrokerProfile.get();
                brokerProfile.setName(brokerProfileDto.getName());
                brokerProfile.setDocNumber(brokerProfileDto.getDocNumber());
                brokerProfile.setFullAddress(brokerProfileDto.getFullAddress());
                brokerProfile.setCity(brokerProfileDto.getCity());
                // Set other properties as needed
                BrokerProfile updateOne =  brokerProfileRepository.save(brokerProfile);

                BrokerProfileDto updatedDto = new BrokerProfileDto(updateOne);

                return updatedDto;
            } else {
                throw new BrokerProfileNotFoundException("Broker profile not found with ID: " + brokerProfileId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<BrokerProfileDto> getAllBrokerProfiles() {
        List<BrokerProfile> brokerProfiles = brokerProfileRepository.findAll();
        return brokerProfiles.stream()
                .map(BrokerProfileDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BrokerProfile> findBrokerProfileWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<BrokerProfile> brokerProfilePagePage = brokerProfileRepository.findAll(PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.DESC, field))));
        return brokerProfilePagePage;
    }

    @Override
    public BrokerProfileDto getBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException {
        Optional<BrokerProfile> optionalBrokerProfile = brokerProfileRepository.findById(brokerProfileId);
        if (optionalBrokerProfile.isPresent()) {
            return new BrokerProfileDto(optionalBrokerProfile.get());
        } else {
            throw new BrokerProfileNotFoundException("Broker profile not found with ID: " + brokerProfileId);
        }
    }
    @Override
    public void deleteBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException {
        Optional<BrokerProfile> optionalBrokerProfile = brokerProfileRepository.findById(brokerProfileId);
        if (optionalBrokerProfile.isPresent()) {
            brokerProfileRepository.deleteById(brokerProfileId);
        } else {
            throw new BrokerProfileNotFoundException("Broker profile not found with ID: " + brokerProfileId);
        }
    }

    @Override
    public List<BrokerProfileDto> getBrokerByUserId(Integer userId) throws BrokerProfileNotFoundException {
        List<BrokerProfile> brokers = brokerProfileRepository.findByUserUserId(userId);
        if (brokers.isEmpty()) {
            throw new BrokerProfileNotFoundException("No Broker found for user ID: " + userId);
        }
        return brokers.stream().map(BrokerProfileDto::new).collect(Collectors.toList());
    }


}
