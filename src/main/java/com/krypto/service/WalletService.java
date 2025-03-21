package com.krypto.service;
import com.krypto.model.Order;
import com.krypto.model.User;
import com.krypto.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet,Long money);
    Wallet findWalletById(Long Id) throws Exception;
    Wallet walletToWalletTransaction(User sender,Wallet receiverWallet,Long money) throws Exception;
    Wallet payOrderPayment(Order order, User user);
}
