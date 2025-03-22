package com.krypto.controller;

import com.krypto.config.JwtProvider;
import com.krypto.model.TwoFactorOTP;
import com.krypto.repository.UserRepository;
import com.krypto.model.User;
import com.krypto.response.AuthResponse;
import com.krypto.service.CustomerUserDetailService;
import com.krypto.service.EmailService;
import com.krypto.service.TwoFactorOTPService;
import com.krypto.service.WatchlistService;
import com.krypto.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")


public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;
    @Autowired
    private TwoFactorOTPService twoFactorOTPService;
    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private EmailService emailService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>register(@RequestBody User user) throws Exception {

        User isEmailExist=userRepository.findByEmail(user.getEmail());

        if(isEmailExist!=null)
        {
            throw new Exception("email already exists");
        }
        User newUser=new User();
        newUser.setFullname(user.getFullname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        User savedUser=userRepository.save((newUser));

        watchlistService.createWatchList(savedUser);


        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt= JwtProvider.generateToken(auth);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Register Success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>login(@RequestBody User user) throws Exception {

        String username=user.getEmail();
        String password=user.getPassword();

        Authentication auth =authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt= JwtProvider.generateToken(auth);
        User authUser = userRepository.findByEmail(username);

        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse res=new AuthResponse();
            res.setMessage("Two factor Auth is Enabled");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUser(authUser.getId());
            if(oldTwoFactorOTP!=null){
                twoFactorOTPService.deleteTwoFactorOTP(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOTP=twoFactorOTPService.createTwoFactorOTP(authUser,otp,jwt);
            emailService.sendVerificationOtpEmail(username,otp);

            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=customerUserDetailService.loadUserByUsername(username);

        if(userDetails==null)
        {
            throw new BadCredentialsException("invalid Username");
    }
        if(!password.equals(userDetails.getPassword()))
        {
        throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigininOtp(@PathVariable String otp,@RequestParam String id) throws Exception {

        TwoFactorOTP twoFactorOTP=twoFactorOTPService.findById(id);

        if(twoFactorOTPService.verifyTwoFactorOTP(twoFactorOTP,otp)){
            AuthResponse res=new AuthResponse();
            res.setMessage("Two factor Auth is Enabled");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOTP.getId());
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }
}
