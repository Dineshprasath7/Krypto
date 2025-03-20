package com.krypto.service;

import com.krypto.domain.VerificationType;
import com.krypto.model.VerificationCode;
import com.krypto.model.User;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user , VerificationType verificationType);

    VerificationCode getVerificationCodeById (Long id) throws Exception;

    VerificationCode getVerificationCodeByUser (Long userid);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
