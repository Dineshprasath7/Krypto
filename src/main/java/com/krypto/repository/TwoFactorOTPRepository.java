package com.krypto.repository;

import com.krypto.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP,String> {

    TwoFactorOTP findByUserId (Long userId);

}
