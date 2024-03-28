package com.culture.sharding.orders.repository;

import com.culture.sharding.orders.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c WHERE orgoid = ?1")
    Customer findByOrgoid(String orgoid);



}
