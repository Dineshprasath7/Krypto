package com.krypto.repository;

import com.krypto.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface WalletRepository extends JpaRepository <Wallet,Long>
{
    Wallet findByUserId(Long userId);

}
