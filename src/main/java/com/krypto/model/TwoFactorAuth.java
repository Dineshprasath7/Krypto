package com.krypto.model;

import com.krypto.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled=false;

    private VerificationType sendTo;
}
