package com.krypto.controller;

import com.krypto.domain.OrderType;
import com.krypto.model.Coin;
import com.krypto.model.Order;
import com.krypto.request.CreateOrderRequest;
import com.krypto.service.CoinService;
import com.krypto.service.OrderService;
import com.krypto.service.UserService;
import com.krypto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @PostMapping("/pay")
    public ResponseEntity<Order>payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());
        Order order=orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("order_id") Long orderId)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.getOrderById(orderId);
        if(order.getUser().getId()==(user.getId())){
            return ResponseEntity.ok(order);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrderForUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType orderType,
            @RequestParam(required = false) String assetsSymbol ) throws Exception {
                Long userId=userService.findUserProfileByJwt(jwt).getId();
                List<Order> userOrders=orderService.getAllOrderOfUser(userId,orderType,assetsSymbol);
                return ResponseEntity.ok(userOrders);
    }

}
