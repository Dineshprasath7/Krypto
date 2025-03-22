package com.krypto.service;

import com.krypto.domain.PaymentMethod;
import com.krypto.model.PaymentOrder;
import com.krypto.model.User;
import com.krypto.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProceedPaymentOrder (PaymentOrder paymentOrder, String paymentId) throws Exception;

    PaymentResponse createRazorpayPaymentLink(User user, Long Amount, Long orderId) throws Exception;

    PaymentResponse createStripePaymentLink(User user, Long Amount, Long orderId) throws Exception;
}
