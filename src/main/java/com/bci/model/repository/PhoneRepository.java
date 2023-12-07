package com.bci.model.repository;

import com.bci.model.entity.PhoneEntity;
import com.bci.model.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {
	
	List<PhoneEntity> findByUser(UserEntity user);
}