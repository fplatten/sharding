package com.culture.sharding.orders.repository;

import com.culture.sharding.orders.model.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByCustomerId(Long customerId);

}
