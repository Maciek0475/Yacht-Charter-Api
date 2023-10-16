package com.mac2work.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.orders.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
