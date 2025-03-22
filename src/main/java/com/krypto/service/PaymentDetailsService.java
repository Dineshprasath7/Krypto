package com.krypto.service;

import com.krypto.model.PaymentDetails;
import com.krypto.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user);

    public PaymentDetails getUsersPaymentDetails(User user);


}
