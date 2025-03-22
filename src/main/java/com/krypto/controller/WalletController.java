package com.krypto.controller;

import com.krypto.domain.WalletTransactionType;
import com.krypto.model.*;
import com.krypto.response.PaymentResponse;
import com.krypto.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @Autowired
    private PaymentService paymentService;


    @GetMapping("/api/wallet")
    public ResponseEntity<?> getUserWallet(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

//    @GetMapping("/api/wallet/transactions")
//    public ResponseEntity<List<WalletTransaction>> getWalletTransaction(@RequestHeader("Authorization")String jwt) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//
//        Wallet wallet = walletService.getUserWallet(user);
//
//        List<WalletTransaction> transactions=walletService.get(wallet,null);
//
//        return new ResponseEntity<>(transactions, HttpStatus.OK);
//    }

    @PutMapping("/api/wallet/deposit/amount/{amount}")
    public ResponseEntity<PaymentResponse> depositMoney(@RequestHeader("Authorization")String jwt, @PathVariable Long amount) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
//        PaymentResponse res = walleteService.depositFunds(user,amount);
        PaymentResponse res = new PaymentResponse();
        res.setPaymentURL("deposite success");
        walletService.addBalance(wallet, amount);

        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addMoneyToWallet(@RequestHeader("Authorization")String jwt, @RequestParam(name="order_id") Long orderId, @RequestParam(name="payment_id")String paymentId) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);


        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status=paymentService.ProceedPaymentOrder(order,paymentId);
        PaymentResponse res = new PaymentResponse();
        res.setPaymentURL("Deposit success");

        if(status){
            wallet=walletService.addBalance(wallet, order.getAmount());
        }


        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }


    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization")String jwt, @PathVariable Long walletId, @RequestBody WalletTransaction req) throws Exception {
        User senderUser =userService.findUserProfileByJwt(jwt);


        Wallet reciverWallet = walletService.findWalletById(walletId);

        Wallet wallet = walletService.walletToWalletTransaction(senderUser,reciverWallet, req.getAmount());
//       WalletTransaction walletTransaction=walletTransactionService.createTransaction(
//                wallet,
//                WalletTransactionType.WALLET_TRANSFER,reciverWallet.getId().toString(),
//                req.getPurpose(),
//                -req.getAmount()
//        );

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);

    }


    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization")String jwt,@PathVariable Long orderId) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Order order=orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order,user);
        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }

    @PutMapping("/api/wallet/balance")
    public ResponseEntity<Wallet> addBalanceToWallet( @RequestHeader("Authorization")String jwt,@RequestParam (name = "order_Id") Long orderId,@RequestParam(name = "payment_id")String paymentId) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        PaymentOrder order= paymentService.getPaymentOrderById(orderId);
        Boolean Status = paymentService.ProceedPaymentOrder(order,paymentId);
        if(Status){
            wallet=walletService.addBalance(wallet, order.getAmount());

        }

        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }



}