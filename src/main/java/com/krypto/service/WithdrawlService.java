package com.krypto.service;

import com.krypto.model.User;
import com.krypto.model.Withdrawl;

import java.util.List;

public interface WithdrawlService {
    Withdrawl requestWithdrawl(Long amount, User user);
    Withdrawl procedWithdrawl(Long WithdrawlId,boolean accept) throws Exception;
    List<Withdrawl> getUsersWithdrawlHistory(User user);
    List<Withdrawl> getAllWithdrawlRequest();
}