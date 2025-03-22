package com.krypto.model;

import com.krypto.domain.WithdrawlStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private WithdrawlStatus status;
    private Long amount;
    @ManyToOne
    private User user;
    private LocalDateTime date =LocalDateTime.now();

}
