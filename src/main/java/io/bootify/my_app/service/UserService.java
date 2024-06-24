package io.bootify.my_app.service;


import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserAlreadyExistException;
import io.bootify.my_app.exception.UserNotFound;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException;
    public List<UserDto> getAllUsers(int pageNo, int pageSize) throws UserNotFound, PageNotFoundException;
    public UserDto getUserById(Integer userId) throws ResourceNotFoundException;

    public UserDto updateUser(UserDto userDto , Integer userId);

   public void deleteUser(Integer userId) throws ResourceNotFoundException;
}
