package com.bci.model.repository;

import com.bci.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	boolean existsByEmail(String email);
	Optional<UserEntity> findByUserId(String userId);
	void deleteByUserId(String userId);
	List<UserEntity> findAll();
	
}
