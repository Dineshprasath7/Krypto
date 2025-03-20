package com.krypto.model;

import com.krypto.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;

@Entity
@Data

public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToOne
    private User user;

    private String otp;

    private VerificationType verificationType;

    private String sendTo;

}
