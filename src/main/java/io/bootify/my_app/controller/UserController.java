package io.bootify.my_app.controller;


import io.bootify.my_app.domain.EmailVerification;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.*;
import io.bootify.my_app.repos.EmailVerificationRepository;
import io.bootify.my_app.service.EmailService;
import io.bootify.my_app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService es;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    EmailVerification emailVerification = new EmailVerification();
    // send otp
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody UserDto users) {
        try {
            System.out.println(users.getEmail());

            Random random = new Random();

            String otp = RandomStringUtils.randomNumeric(4);
            System.out.println(otp);
            LocalDateTime localDateTime = LocalDateTime.now();

            String subject = "OTP from LMS";
            String emailTemplate = "<h1>OTP: " + otp + "</h1>";

            String to = users.getEmail();
            boolean flag = this.es.sendEmail(emailTemplate, subject, to);
            if (flag) {
                this.es.saveEmail(users.getEmail(), otp, localDateTime);
                return "OTP has been sent, please verify OTP.";
            } else {
                return "Please try again..!!";
            }
        } catch (Exception e) {

            e.printStackTrace(); // or log.error("Exception occurred: ", e);
            return "An error occurred while processing your request. Please try again later.";
        }

    }

    // verify otp
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp, @RequestParam String email) throws OtpExpiredException, InvalidOtpException {

        EmailVerification emailVerification = emailVerificationRepository.findByEmail(email);
        if (emailVerification == null) {
            throw new InvalidOtpException("Invalid OTP");
        } else {
            if (emailVerification.getOtp().equals(otp)) {
                LocalDateTime creationTime = emailVerification.getCreationTime();
                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(creationTime, currentTime);

                if (duration.toMinutes() <= 3) {
                    emailVerification.setStatus("Verified");
                    emailVerificationRepository.save(emailVerification);
                    return "Verified";
                } else {
                    throw new OtpExpiredException("OTP has expired");
                }
            } else {
                throw new InvalidOtpException("Invalid OTP");
            }

        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto, HttpSession session) throws Exception, UserAlreadyExistException {

        EmailVerification emailVerification = emailVerificationRepository.findByEmail(userDto.getEmail());

        if (emailVerification != null && emailVerification.getStatus().equals("Verified")) {
            // OTP is verified
            if (userDto.getPassword() != null) {
                userDto.setPassword(this.encoder.encode(userDto.getPassword()));
            }

            UserDto registeredUser = this.userService.createUser(userDto);

            if (registeredUser != null) {
                return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (emailVerification != null && emailVerification.getStatus().equals("Not verified")) {
            // OTP verification pending
            return new ResponseEntity<>("OTP verification pending", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("OTP verification is pending or email not found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto , @PathVariable Integer userId)
    {
        UserDto updatedUser = userService.updateUser(userDto , userId);

        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(userDto , HttpStatus.OK);

        return responseEntity;
    }


    @DeleteMapping("delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) throws ResourceNotFoundException {

        userService.deleteUser(userId);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(null , HttpStatus.NO_CONTENT);

        return responseEntity;
    }

//    @GetMapping("/getByBrokerProfileId")
//    public ResponseEntity<?> getUsersByBrokerProfileId(@RequestParam Integer brokerProfileId) {
//        try {
//            List<UserDto> leases = userService.getUserByBrokerProfileId(brokerProfileId);
//            return new ResponseEntity<>(leases, HttpStatus.OK);
//        } catch (LeaseNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leases found for Broker Profile ID: " + brokerProfileId);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get leases by Broker Profile ID: " + e.getMessage());
//        }
//    }
}
