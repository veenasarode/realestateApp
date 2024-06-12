package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Roles;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.domain.UserRole;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.UserAlreadyExistException;
import io.bootify.my_app.repos.RolesRepo;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
}
