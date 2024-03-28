package com.culture.sharding.orders.presentation;

import com.culture.sharding.orders.model.CustomerOrder;
import com.culture.sharding.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerOrderController {

    private final OrderService orderService;

    @Autowired
    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orgoid}/orders")
    public List<CustomerOrder> getCustomerOrders(@PathVariable String orgoid) {

        System.out.println("Give a number: " + orgoid);

        List<CustomerOrder> orders = orderService.getOrdersByCustomerId(orgoid);

        return orders;
    }

}
