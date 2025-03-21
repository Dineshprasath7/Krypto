package com.krypto.controller;


import com.krypto.domain.WalletTransactionType;
import com.krypto.model.Order;
import com.krypto.model.Wallet;
import com.krypto.model.WalletTransaction;
import com.krypto.service.UserService;
import com.krypto.service.WalletService;
import com.krypto.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.krypto.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    private UserService userService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet>getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Wallet wallet=walletService.getUserWallet(user);


        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/wallet/${walletId}/transfer")
    public ResponseEntity<Wallet>walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
    )throws Exception {

        User senderUser=userService.findUserProfileByJwt(jwt);
        Wallet recieverWallet=walletService.findWalletById(walletId);
        Wallet wallet=walletService.walletToWalletTransaction(senderUser,
                recieverWallet,
                req.getAmount());
    return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
    @PostMapping("/api/wallet/order/${orderId}/pay")
    public ResponseEntity<Wallet>payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId
    )throws Exception {

        User senderUser=userService.findUserProfileByJwt(jwt);
      Order order=orderService.getOrderById(orderId);
        Wallet wallet=walletService.payOrderPayment(order,senderUser);
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
}
