package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Roles;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.domain.UserRole;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserAlreadyExistException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.repos.RolesRepo;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RolesRepo rolesRepo;


    @Override
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException {

        User existingUser = userRepo.findByEmail(userDto.getEmail());


        if (existingUser != null) {
            System.out.println("User already exists");
            throw new UserAlreadyExistException("User already exists");
        } else {

            User user = new User(userDto);

            Set<UserRole> roles = new HashSet<>();
            Roles role = new Roles();
            role.setId(44);
            role.setName("NORMAL");
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            roles.add(userRole);


            for (UserRole ur : roles) {
                this.rolesRepo.save(ur.getRole());
            }

            user.getUserRoles().addAll(roles);


            User registeredUser = userRepo.save(user);


            /*return new UserDto(registeredUser);*/
        }

        return userDto;
    }

    @Override
    public UserDto getUserById(Integer userId) throws ResourceNotFoundException {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        return new UserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        Optional<User> optionalUser = userRepo.findById(userId);

        UserDto  updatedUser = null;

        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();//data from database

            user.setName(userDto.getName());
            user.setAddress(userDto.getAddress());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setMoNumber(userDto.getMoNumber());

            updatedUser = new UserDto(user);

            userRepo.save(user);

        }
        return updatedUser;
    }

    @Override
    public void deleteUser(Integer userId) throws ResourceNotFoundException {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        userRepo.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers(int pageNo, int pageSize) throws UserNotFound, PageNotFoundException {
        List<User> listOfCar = userRepo.getActivateUserOrderedByCreatedAtDesc();
        if (listOfCar.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        int totalCars = listOfCar.size();
        int totalPages = (int) Math.ceil((double) totalCars / pageSize);

        if (pageNo < 0 || pageNo >= totalPages) {
            throw new PageNotFoundException("Page not found");
        }

        int pageStart = (pageNo) * pageSize;
        int pageEnd = Math.min(pageStart + pageSize, totalCars);

        List<UserDto> listOfUserDto = new ArrayList<>();
        for (int i = pageStart; i < pageEnd; i++) {
            User user = listOfCar.get(i);
            UserDto userDto = new UserDto(user);
            userDto.setUserId(user.getUserId());
            listOfUserDto.add(userDto);
        }

        return listOfUserDto;
    }
}
