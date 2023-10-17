package com.userservice.repository;

import com.userservice.models.ERole;
import com.userservice.models.EmailLogger;
import com.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailLoggerRepository extends JpaRepository<EmailLogger, Long> {

}
