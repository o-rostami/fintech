package com.example.fintech.model.user.dao;

import java.util.Optional;

import com.example.fintech.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);
}
