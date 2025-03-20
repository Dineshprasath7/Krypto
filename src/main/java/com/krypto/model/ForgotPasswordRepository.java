package com.krypto.model;

import jakarta.persistence.Lob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String> {

     ForgotPasswordToken findByUserId(Long Id);
}
