package com.krypto.service;

import com.krypto.domain.WalletTransactionType;
import com.krypto.model.Wallet;
import com.krypto.model.WalletTransaction;
import com.krypto.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public WalletTransaction createTransaction(Wallet wallet, WalletTransactionType transactionType, String transferId, String purpose, Long amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setDate(LocalDate.now());
        transaction.setWalletTransactionType(transactionType);
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);

        return walletTransactionRepository.save(transaction);
    }

    @Override
    public List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType transactionType) {
        return walletTransactionRepository.findByWalletOrderByDateDesc(wallet);
    }
}
