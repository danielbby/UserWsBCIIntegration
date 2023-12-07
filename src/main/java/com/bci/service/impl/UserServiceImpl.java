package com.bci.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.bci.model.Phone;
import com.bci.model.UserRequest;
import com.bci.model.UserResponse;
import com.bci.model.entity.PhoneEntity;
import com.bci.model.entity.UserEntity;
import com.bci.model.repository.PhoneRepository;
import com.bci.model.repository.UserRepository;
import com.bci.service.UserService;
import com.bci.utils.*;


public class UserServiceImpl implements UserService {
	Logger LOG = LogManager.getLogger(UserServiceImpl.class);

	private UserRepository userRepository = null;
	private PhoneRepository phoneRepository = null;

	public UserServiceImpl(UserRepository userRepository, PhoneRepository phoneRepository) {
		this.userRepository = userRepository;
		this.phoneRepository = phoneRepository;
	}

	@Override
	public UserResponse addUser(UserRequest request) throws Exception { 

		UserEntity user = new UserEntity();
		UserResponse response = new UserResponse(request);

		user.setName(Encryption.encrypt(request.getName()));
		if (userRepository.existsByEmail(Encryption.encrypt(request.getEmail()))) {
			response.setMessage("ERROR - El email proporcionado ["+
					request.getEmail() + "] ya existe en la base de datos. Verifíquelo e intente nuevamente");
			return response;
		} else if (!EmailValidator.isValidEmail(request.getEmail())) {
			response.setMessage("ERROR - Debe proporcionar un email válido. El email ["+
					request.getEmail() + "] no cumple con el formato correcto. Verifíquelo e intente nuevamente");
			return response;
		} else {
			user.setEmail(Encryption.encrypt(request.getEmail()));
		}

		if (!PasswordValidator.isValid(request.getPassword())) {
			response.setMessage("ERROR - La contraseña debe contener al menos una letra mayúscula, "
					+ "una minúscula, un dígito numérico y un caracter especial @$.!%*?&");
			return response;
		} else {
			user.setPassword(Encryption.encrypt(request.getPassword()));	
		}

		user.setCreated(DateTime.getCurrentDate());
		user.setLastLogin(DateTime.getCurrentDate());
		user.setModified(null);
		user.setActive(true);
		user.setUserId(UUIDGenerator.generateUserId());
		user.setToken(TokenGenerator.generateToken(	request.getEmail(), 
				DateTime.getActualStringTimeStamp(),
				user.getUserId()));

		UserEntity savedUser = this.userRepository.save(user);

		if (request.getPhones() != null && !request.getPhones().isEmpty()) {
			for (Phone phoneRequest : request.getPhones()) {
				PhoneEntity phone = new PhoneEntity();
				phone.setNumber(Encryption.encrypt(phoneRequest.getNumber()));
				phone.setCityCode(Encryption.encrypt(phoneRequest.getCityCode()));
				phone.setCountryCode(Encryption.encrypt(phoneRequest.getCountryCode()));
				phone.setUser(user);
				phoneRepository.save(phone);
			}
		}

		response.setUserId(savedUser.getUserId());
		response.setCreated(savedUser.getCreated().toString());
		response.setLast_login(savedUser.getCreated().toString());
		response.setIsactive(savedUser.isActive() ? "true" : "false");
		response.setToken(savedUser.getToken());
		response.setName(Encryption.decrypt(savedUser.getName()));
		response.setEmail(Encryption.decrypt(savedUser.getEmail()));

		return response;
	}

	@Override
	public UserResponse getUserById(UserRequest request) throws Exception {
		String userId = request.getUserId();
		UserResponse response = new UserResponse(request);
		Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

		if (userOptional.isPresent()) {
			UserEntity userEntity = userOptional.get();
			response = populateUser(userEntity);
			return response;
		}
		response.setMessage("ERROR - Usuario no encontrado en Base de Datos. UserId: " + request.getUserId());
		return response;
	}

	@Override
	public List<UserResponse> getAllUsers() throws Exception {
		List<UserEntity> allUsers = userRepository.findAll();
		List<UserResponse> userResponse = new ArrayList<>();

		for (UserEntity userEntity : allUsers) {
			UserResponse response = new UserResponse();
			response = populateUser(userEntity);
			userResponse.add(response);
		}
		if (null != userResponse && userResponse.isEmpty()) {
			UserResponse response = new UserResponse();
			response.setMessage("ERROR - No se encontraron usuarios en la Base de Datos");
			userResponse.add(response);
		} 
		return userResponse;

	}

	private UserResponse populateUser(UserEntity userEntity) throws Exception {
		UserResponse response = new UserResponse();
		response.setName(Encryption.decrypt(userEntity.getName()));
		response.setEmail(Encryption.decrypt(userEntity.getEmail()));
		response.setUserId(userEntity.getUserId());
		response.setCreated(userEntity.getCreated().toString());
		response.setLast_login(userEntity.getCreated().toString());
			if (userEntity.getModified() != null)
				response.setModified(userEntity.getModified().toString());
		response.setIsactive(userEntity.isActive() ? "true" : "false");
		response.setToken(userEntity.getToken());

		List<PhoneEntity> phones = phoneRepository.findByUser(userEntity);
		List<Phone> phoneResponse = new ArrayList<>();

		for (PhoneEntity phoneEntity : phones) {
			Phone phone = new Phone();
			phone.setNumber(Encryption.decrypt(phoneEntity.getNumber()));
			phone.setCityCode(Encryption.decrypt(phoneEntity.getCityCode()));
			phone.setCountryCode(Encryption.decrypt(phoneEntity.getCountryCode()));
			phoneResponse.add(phone);
		}
		response.setPhones(phoneResponse);
		return response;
	}

	@Transactional
	@Override
	public UserResponse deleteUserById(UserRequest request) {

		String userId = request.getUserId();
		UserResponse response = new UserResponse(request);
		Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			userRepository.deleteByUserId(userId);
			response.setMessage("Usuario eliminado de la Base de Datos");
		} else {
			response.setMessage("ERROR - Usuario no encontrado en Base de Datos");
		}
		return response;
	}

	@Override
	public UserResponse updateUser(UserRequest request) {
		String userId = request.getUserId();
		UserResponse response = new UserResponse(request);
		Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			try {
				Boolean mod = false;
				UserEntity foundUser = userOptional.get();
				if (request.getName() != null) {
					String inDbName = foundUser.getName();
					String upName = Encryption.encrypt(request.getName());
					if (!upName.equals(inDbName)) {
						foundUser.setName(upName);
						mod = true;
					}
				}
				if (request.getEmail() != null) {
					if (!userRepository.existsByEmail(Encryption.encrypt(request.getEmail()))) {
						if (!EmailValidator.isValidEmail(request.getEmail())) {
							response.setMessage("ERROR - Debe proporcionar un email válido. El email ["+
									request.getEmail() + "] no cumple con el formato correcto. Verifíquelo e intente nuevamente");
							return response;
						} else {
							foundUser.setEmail(Encryption.encrypt(request.getEmail()));
							mod = true;
						}					
					}
				}
				if (request.getPassword() != null) {
					String upPass = Encryption.encrypt(request.getPassword());
					String pass = foundUser.getPassword();
					String pass1 = foundUser.getPasswordOld1();
					String pass2 = foundUser.getPasswordOld2();

					if (upPass.equals(pass) || upPass.equals(pass1) || upPass.equals(pass2)) {
						response.setMessage("ERROR - El password no puede ser igual a los tres (3) passwords anteriores");
						return response;
					}					
					if (!PasswordValidator.isValid(request.getPassword())) {
						response.setMessage("ERROR - La contraseña debe contener al menos una letra mayúscula, "
								+ "una minúscula, un dígito numérico y un caracter especial @$.!%*?&");
						return response;
					} else {
						foundUser.setPasswordOld2(pass1);
						foundUser.setPasswordOld1(pass);
						foundUser.setPassword(upPass);	
						mod = true;
					}
				}
				
				if (request.getPhones() != null && !request.getPhones().isEmpty()) {
			        List<PhoneEntity> phones = phoneRepository.findByUser(foundUser);
			        if (phones != null && !phones.isEmpty()){
			        	phoneRepository.deleteAll(phones);
			        }

					for (Phone phoneRequest : request.getPhones()) {
						PhoneEntity phone = new PhoneEntity();
						phone.setNumber(Encryption.encrypt(phoneRequest.getNumber()));
						phone.setCityCode(Encryption.encrypt(phoneRequest.getCityCode()));
						phone.setCountryCode(Encryption.encrypt(phoneRequest.getCountryCode()));
						phone.setUser(foundUser);
						phoneRepository.save(phone);
					}
					mod = true;
				}
				
				if (request.getIsactive() != null) {
					String upActive = request.getIsactive();
					if (foundUser.isActive()){
						if ("false".equals(upActive)){
							foundUser.setActive(false);
							mod = true;
						}	
					} else {
						if ("true".equals(upActive)) {
							foundUser.setActive(true);
							mod = true;
						}
					}
				}
				
				if (mod) {
					foundUser.setModified(DateTime.getCurrentDate());
					foundUser.setLastLogin(DateTime.getCurrentDate());
					foundUser.setToken(TokenGenerator.generateToken(request.getEmail(), 
												DateTime.getActualStringTimeStamp(),
												request.getUserId()));
					userRepository.save(foundUser);
					response.setUserId(userId);
					response.setToken(foundUser.getToken());
					response.setMessage("Usuario actualizado correctamente");
				} else {
					response.setMessage("No se modificaron datos del usuario");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			response.setMessage("ERROR - Usuario no encontrado en Base de Datos");
		}

		return response;
	}

}
