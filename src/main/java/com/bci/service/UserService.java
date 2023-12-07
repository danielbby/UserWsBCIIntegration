package com.bci.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bci.model.UserRequest;
import com.bci.model.UserResponse;

@Service
public interface UserService {

	public UserResponse addUser (UserRequest request) throws Exception;
	public UserResponse getUserById(UserRequest request) throws Exception;
	public List<UserResponse> getAllUsers() throws Exception;
	public UserResponse deleteUserById(UserRequest request);
	public UserResponse updateUser(UserRequest request);
	
}
