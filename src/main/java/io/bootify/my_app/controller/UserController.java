package io.bootify.my_app.controller;


import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.UserAlreadyExistException;
import io.bootify.my_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/add")
    public String addUser(@RequestBody UserDto userDto) throws UserAlreadyExistException {
        if (userDto.getPassword() != null) {
            userDto.setPassword(this.encoder.encode(userDto.getPassword()));
        }

       this.userService.createUser(userDto);

        return "success";
    }

}
