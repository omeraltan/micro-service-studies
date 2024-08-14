package com.omer.repository;

import com.omer.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Boolean existsByUsernameAndPassword(String userName, String password);
}
