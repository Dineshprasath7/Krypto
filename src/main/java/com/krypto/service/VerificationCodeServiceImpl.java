package com.krypto.service;

import com.krypto.domain.VerificationType;
import com.krypto.model.User;
import com.krypto.model.VerificationCode;
import com.krypto.repository.VerificationCodeRepository;
import com.krypto.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode  verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception{
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);

        if(verificationCode.isEmpty()) {
            throw new Exception("Verification code not found");
        }

        return verificationCode.get();


    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userid) {
        return verificationCodeRepository.findByUserId(userid);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}
