package com.sam.user_service.controller;

import com.sam.user_service.dto.UserRequest;
import com.sam.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return "User created";
    }

    @GetMapping
    public UserRequest getUser(Long Id) {
        return userService.getUser(Id);
    }

    @GetMapping("/getUserByEmail")
    public UserRequest getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/getUserByName")
    public UserRequest getUserByName(String name){
        return userService.getUserByName(name);
    }

    @GetMapping("/getAllUsers")
    public List<UserRequest> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/getUsersByUserStatus")
    public List<UserRequest> getAllUsersByUserStatus(String userStatus) {
        return userService.getUsersByStatus(userStatus);
    }
    @GetMapping("/getUsersByUserType")
    public List<UserRequest> getAllUsersByUserType(String userType) {
        return userService.getUsersByType(userType);
    }

    @DeleteMapping()
    public String deleteUser(Long Id){
        userService.deleteUser(Id);
        return "User: "+Id+" Deleted";
    }

    @PutMapping
    public String updateUser(@RequestBody UserRequest userRequest){
        userService.updateUser(userRequest);
        return "User: "+userRequest.name()+" Updated";
    }

    @PutMapping("/updateUserStatus")
    public String updateUserStatus(Long id, String status) {
        userService.updateUserStatus(id, status);
        return "User: " + id + " Status Updated to " + status;
    }

    @PutMapping("/updateUserCoordinates")
    public String updateUserCoordinates(Long id, Double lat, Double lon) {
        userService.updateUserCoordinates(id, lat, lon);
        return "User: " + id + " Coordinates Updated to (" + lat + ", " + lon + ")";
    }

}
