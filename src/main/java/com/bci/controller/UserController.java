package com.bci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bci.model.UserRequest;
import com.bci.model.UserResponse;
import com.bci.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/BCI/User")
    public UserResponse addUser(@RequestBody UserRequest request) throws Exception {
        return userService.addUser(request);
    }
    
    @GetMapping("/BCI/User/GetById")
    public UserResponse getUserById(@RequestBody UserRequest request) throws Exception {
        return userService.getUserById(request);
    }

    @GetMapping("/BCI/User/GetAll")
    public List<UserResponse> getAllUsers() throws Exception {
        return (List<UserResponse>) userService.getAllUsers();
    }
    
    @DeleteMapping("/BCI/User/DeleteById")
    public UserResponse deleteUserById(@RequestBody UserRequest request) throws Exception {
        return userService.deleteUserById(request);
    }
    
    @PutMapping("/BCI/User/UpdateUser")
    public UserResponse updateUser(@RequestBody UserRequest request) throws Exception{
    	return userService.updateUser(request);
    }
    
}
