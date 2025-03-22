package com.krypto.service;

import com.krypto.domain.WalletTransactionType;
import com.krypto.model.WalletTransaction;
import com.krypto.model.Wallet;

import java.util.List;

public interface WalletTransactionService {
    WalletTransaction createTransaction(Wallet wallet
    , WalletTransactionType transactionType,
    String transferId,String purpose,Long amount);
    List<WalletTransaction> getTransactions(Wallet wallet,WalletTransactionType transactionType);
}
