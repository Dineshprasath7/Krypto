package com.krypto.repository;

import com.krypto.model.Wallet;
import com.krypto.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
    List<WalletTransaction> findByWalletOrderByDateDesc(Wallet wallet);

}
