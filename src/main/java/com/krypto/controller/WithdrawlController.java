package com.krypto.controller;

import com.krypto.model.User;
import com.krypto.model.Wallet;
import com.krypto.model.WalletTransaction;
import com.krypto.model.Withdrawl;
import com.krypto.service.UserService;
import com.krypto.service.WalletService;
import com.krypto.service.WithdrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class WithdrawlController {

    @Autowired
    private WithdrawlService WithdrawlService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

  //  @Autowired
  //  private  WalletTransactionService walletTransactionService;

    @PostMapping("/api/Withdrawl/{amount}")
    public ResponseEntity<?> WithdrawlRequest(@PathVariable Long amount, @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Wallet userWallet=walletService.getUserWallet(user);

        Withdrawl Withdrawl=WithdrawlService.requestWithdrawl(amount,user);
        walletService.addBalance(userWallet, -Withdrawl.getAmount());

//        WalletTransaction walletTransaction = walletTransactionService.createTransaction(userWallet,
//                WalletTransactionType.Withdrawl,null,
//                "bank account Withdrawl",
//                Withdrawl.getAmount()
//        );

        return new ResponseEntity<>(Withdrawl, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/Withdrawl/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawl(@PathVariable Long id, @PathVariable boolean accept, @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Withdrawl Withdrawl=WithdrawlService.procedWithdrawl(id,accept);
        Wallet userWallet=walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalance(userWallet, Withdrawl.getAmount());
        }
        return new ResponseEntity<>(Withdrawl, HttpStatus.OK);
    }

    @GetMapping("/api/Withdrawl")
    public ResponseEntity<List<Withdrawl>> getWithdrawlHistory(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Withdrawl> Withdrawl=WithdrawlService.getUsersWithdrawlHistory(user);
        return new ResponseEntity<>(Withdrawl, HttpStatus.OK);
    }

    @GetMapping("/api/admin/Withdrawl")
    public ResponseEntity<List<Withdrawl>> getAllWithdrawlRequest(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Withdrawl> Withdrawl=WithdrawlService.getAllWithdrawlRequest();
        return new ResponseEntity<>(Withdrawl, HttpStatus.OK);
    }
}
