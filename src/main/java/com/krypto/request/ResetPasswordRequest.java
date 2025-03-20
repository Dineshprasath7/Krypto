package com.krypto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String otp;
    private String password;
}
