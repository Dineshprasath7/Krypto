package com.krypto.service;

import com.krypto.domain.VerificationType;
import com.krypto.model.ForgotPasswordToken;
import com.krypto.model.User;
public interface ForgotPasswordService {
ForgotPasswordToken createToken(User user, String id,
                                String otp, VerificationType verificationType,String sendTO);

ForgotPasswordToken findById(String id);

ForgotPasswordToken findByUser(Long userId);

void deleteToken(ForgotPasswordToken token);


}
