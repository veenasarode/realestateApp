package io.bootify.my_app.service;

import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;

import java.util.List;
import java.util.Optional;

public interface RentPersonService {
    public List<RentPersonDto> getAllRentPersons(int pageNo, int pageSize) throws UserNotFound, PageNotFoundException, ResourceNotFoundException;
    RentPersonDto getRentPersonById(Integer rentPersonId);
    RentPersonDto createRentPerson(RentPersonDto rentPersonDTO);
    RentPersonDto updateRentPerson(Integer id, RentPersonDto rentPersonDTO);
    void deleteRentPerson(Integer id);
}
