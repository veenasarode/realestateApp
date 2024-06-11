package io.bootify.my_app.service;


import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.UserAlreadyExistException;

public interface UserService {
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException;
}
