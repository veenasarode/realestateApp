package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.repos.BrokerProfileRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.BrokerProfileService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    public void addBrokerProfile(BrokerProfileDto brokerProfileDto) {
        try {
            BrokerProfile brokerProfile = new BrokerProfile(brokerProfileDto);
            brokerProfileRepository.save(brokerProfile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
@Override
    public void updateBrokerProfile(BrokerProfileDto brokerProfileDto, Integer brokerProfileId) {
        try {
            Optional<BrokerProfile> optionalBrokerProfile = brokerProfileRepository.findById(brokerProfileId);

            if (optionalBrokerProfile.isPresent()) {
                BrokerProfile brokerProfile = optionalBrokerProfile.get();
                brokerProfile.setName(brokerProfileDto.getName());
                brokerProfile.setDocNumber(brokerProfileDto.getDocNumber());
                brokerProfile.setFullAddress(brokerProfileDto.getFullAddress());
                brokerProfile.setCity(brokerProfileDto.getCity());
                // Set other properties as needed
                brokerProfileRepository.save(brokerProfile);
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
    public Page<BrokerProfile> findBrokerWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<BrokerProfile> brokerProfilePage = brokerProfileRepository.findAll(PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.DESC, field))));
        return brokerProfilePage;

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
    public List<BrokerProfileDto> getBrokerByUserId(Integer userId) throws ResourceNotFoundException {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        List<BrokerProfile> brokerProfiles = this.brokerProfileRepository.findByUser(user);

        List<BrokerProfileDto> brokerProfileDtos = brokerProfiles.stream().map((post)-> this.modelMapper.map(post , BrokerProfileDto.class)).collect(Collectors.toList());

        return brokerProfileDtos;
    }


}
