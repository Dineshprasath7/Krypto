package com.krypto.service;

import com.krypto.domain.OrderStatus;
import com.krypto.domain.OrderType;
import com.krypto.model.Coin;
import com.krypto.model.Order;
import com.krypto.model.OrderItem;
import com.krypto.model.User;
import com.krypto.repository.OrderItemRepository;
import com.krypto.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service

public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order=new Order();
        order.setUser(user);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found");
        } else {
            return orders.get(0);
        }
    }

    @Override
    public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetsSymbol) {
        return orderRepository.findByUserId(userId);
    }
    private OrderItem createOrderItem(Coin coin ,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }
    @Transactional
    public Order buyAsset(Coin coin,double quantity,User user){
        if(quantity<=0){
            throw new RuntimeException("Quantity should be greater than 0");
        }
        double buyPrice=coin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(coin,quantity,buyPrice,0);
        Order order=createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder=orderRepository.save(order);

        //        create asset

        return savedOrder;

    }
    @Transactional
    public Order sellAsset(Coin coin,double quantity,User user){
        if(quantity<=0){
            throw new RuntimeException("Quantity should be greater than 0");
        }
        double sellPrice=coin.getCurrentPrice();
        double buyPrice=assetToSell.getPrice();
        OrderItem orderItem=createOrderItem(coin,quantity,buyPrice,sellPrice);

        Order order=createOrder(user,orderItem,OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity()>=quantity){
            walletService.payOrderPayment(order,user);
            Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),-quantity);
            if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1) {
                assestService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }
        throw new Exception("Not enough quantity to sell");

        //        create asset

        return savedOrder;

    }


    @Override
    @@Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if(orderType.equals(OrderType.BUY))
        {
            return buyAsset(coin,quantity,user);
        }
        else if(orderType.equals(OrderType.SELL))
        {
            return sellAsset(coin,quantity,user);
        }
        throw new Exception("Invalid Order Type");
    }
}
