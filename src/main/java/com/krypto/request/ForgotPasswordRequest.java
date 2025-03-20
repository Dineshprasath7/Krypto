package com.krypto.request;

import com.krypto.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordRequest
{
private String sendTo;
private VerificationType verificationType;
}
