package io.bootify.my_app.serviceImpl;

import io.bootify.my_app.domain.RentPerson;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.repos.LeaseRepository;
import io.bootify.my_app.repos.RentPersonRepository;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.RentPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentPersonServiceImpl implements RentPersonService {

    @Autowired
    private RentPersonRepository rentPersonRepository;

    @Autowired
    private UserRepo userRepository;  // Assuming you have UserRepository
    @Autowired
    private LeaseRepository leaseRepository;  // Assuming you have LeaseRepository

    @Override
    public List<RentPersonDto> getAllRentPersons() {
        return rentPersonRepository.findAll().stream()
                .map(RentPersonDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RentPersonDto> getRentPersonById(Integer id) {
        return rentPersonRepository.findById(id)
                .map(RentPersonDto::new);
    }

    @Override
    public RentPersonDto createRentPerson(RentPersonDto rentPersonDto) {
        // Find the user by ID or throw an exception if not found
        User user = userRepository.findById(rentPersonDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new RentPerson entity from the DTO
        RentPerson rentPerson = new RentPerson(rentPersonDto);

        // Set the user field on the RentPerson entity
        rentPerson.setUserUser(user);

        // Save the RentPerson entity to the repository
        RentPerson savedRentPerson = rentPersonRepository.save(rentPerson);

        // Convert the saved RentPerson entity back to a DTO
        RentPersonDto rentPersonDto1 = new RentPersonDto(savedRentPerson);

        // Return the DTO
        return rentPersonDto1;
    }

    @Override
    public RentPersonDto updateRentPerson(Integer id, RentPersonDto rentPersonDTO) {
        Optional<RentPerson> optionalRentPerson = rentPersonRepository.findById(id);
        if (optionalRentPerson.isPresent()) {
            User user = userRepository.findById(rentPersonDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
          //  Set<Lease> leases = leaseRepository.findAllById(rentPersonDTO.getLeaseIds()).stream().collect(Collectors.toSet());

            RentPerson rentPerson = optionalRentPerson.get();
            rentPerson.setName(rentPersonDTO.getName());
            rentPerson.setAddress(rentPersonDTO.getAddress());
            rentPerson.setMoNumber(rentPersonDTO.getMoNumber());
            rentPerson.setRentPersoncol(rentPersonDTO.getRentPersoncol());
            rentPerson.setUserUser(user);
          //  rentPerson.setRentPersonLease(leases);

            RentPerson updatedRentPerson = rentPersonRepository.save(rentPerson);
           RentPersonDto rentPersonDto=new RentPersonDto(updatedRentPerson);
           return rentPersonDto;
        } else {
            return null; // Or throw an exception
        }
    }

    @Override
    public void deleteRentPerson(Integer id) {
        if (rentPersonRepository.existsById(id)) {
            rentPersonRepository.deleteById(id);
        }
    }
}
