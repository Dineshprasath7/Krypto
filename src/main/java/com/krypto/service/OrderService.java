package com.krypto.service;

import com.krypto.domain.OrderType;
import com.krypto.model.Coin;
import com.krypto.model.OrderItem;
import com.krypto.model.User;
import com.krypto.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType) ;
    Order getOrderById(Long orderId) ;
    List<Order> getAllOrderOfUser(Long userId,OrderType orderType,String assetsSymbol) ;
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;



}
