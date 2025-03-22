package com.krypto.service;
import com.krypto.model.Order;
import com.krypto.model.User;
import com.krypto.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    public Wallet addBalance(Wallet wallet,Long money);
    public Wallet findWalletById(Long Id) throws Exception;
    public Wallet walletToWalletTransaction(User sender,Wallet receiverWallet,Long money) throws Exception;
    public Wallet payOrderPayment(Order order, User user);
}
