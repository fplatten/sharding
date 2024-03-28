package com.culture.sharding.orders.service;

import com.culture.sharding.orders.model.Customer;
import com.culture.sharding.orders.model.CustomerOrder;
import com.culture.sharding.orders.repository.CustomerRepository;
import com.culture.sharding.orders.repository.DataSourceContextHolder;
import com.culture.sharding.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShardingService shardingService;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ShardingService shardingService) {
        this.orderRepository = orderRepository;
        this.shardingService = shardingService;
        this.customerRepository = customerRepository;
    }


    public List<CustomerOrder> getOrdersByCustomerId(String orgoid) {

        try {

            DataSourceContextHolder.setDataSourceKey(shardingService.getShardingDataSource(orgoid));

            System.out.println("getOrdersByCustomerId: " + orgoid);
            //Iterable<CustomerOrder> orders = orderRepository.findAll();


           //orders.forEach(customerOrder -> System.out.println());

            Customer customer = customerRepository.findByOrgoid(orgoid);

            return customer.getOrders();

        } finally {
            DataSourceContextHolder.clearDataSourceKey();
        }

    }
}
