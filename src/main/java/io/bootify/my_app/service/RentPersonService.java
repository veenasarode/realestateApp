package io.bootify.my_app.service;

import io.bootify.my_app.dto.RentPersonDto;

import java.util.List;
import java.util.Optional;

public interface RentPersonService {
    List<RentPersonDto> getAllRentPersons();
    Optional<RentPersonDto> getRentPersonById(Integer id);
    RentPersonDto createRentPerson(RentPersonDto rentPersonDTO);
    RentPersonDto updateRentPerson(Integer id, RentPersonDto rentPersonDTO);
    void deleteRentPerson(Integer id);
}
