package com.krypto.controller;

import com.krypto.domain.VerificationType;
import com.krypto.model.ForgotPasswordToken;
import com.krypto.model.User;
import com.krypto.model.VerificationCode;
import com.krypto.request.ResetPasswordRequest;
import com.krypto.response.ApiResponse;
import com.krypto.response.AuthResponse;
import com.krypto.service.EmailService;
import com.krypto.service.ForgotPasswordService;
import com.krypto.service.UserService;
import com.krypto.service.VerificationCodeService;
import com.krypto.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import com.krypto.request.ForgotPasswordRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public VerificationCodeService verificationCodeService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;
    private String jwt;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        verificationCodeService.deleteVerificationCodeById(verificationCode);

        return new ResponseEntity<String>("Verification OTP Successfuly Sent", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp, @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)
                ? verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo,
                    user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Wrong otp");
    }
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordRequest req) throws Exception {

        User user=userService.findUserByEmail(req.getSendTo());
        String otp= OtpUtils.generateOTP();
        UUID uuid= UUID.randomUUID();
        String id=uuid.toString();
        ForgotPasswordToken token=forgotPasswordService.findByUser(user.getId());
        if(token==null){
            token=forgotPasswordService.createToken(user,id,otp,req.getVerificationType(),req.getSendTo());
        }
        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(),token.getOtp());
        }
        AuthResponse response=new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset Otp sent Successfully");


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword (
                @RequestParam String id,
             @RequestBody ResetPasswordRequest req,
             @RequestHeader("Authorization") String jwt)
            throws Exception {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());

        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
            ApiResponse response=new ApiResponse();
            response.setMessage("Password Updated Successfully");
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }
        throw new Exception("Otp Not correct");
    }

}
