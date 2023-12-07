package com.bci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bci.model.repository.PhoneRepository;
import com.bci.model.repository.UserRepository;
import com.bci.service.UserService;
import com.bci.service.impl.UserServiceImpl;

@SpringBootApplication
public class UserWsBCIIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserWsBCIIntegrationApplication.class, args);
    }

    @Bean
    UserService userService(UserRepository userRepository, PhoneRepository phoneRepository) {
        return new UserServiceImpl(userRepository, phoneRepository);
    }

}
