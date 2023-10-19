package com.mac2work.orders.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mac2work.orders.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findAllByUserId(Long userId);

	Order findByUserId(Long userId);

	@Query("select o from orders o where o.userId = ?1 and o.to < ?2")
	List<Order> findAllArchivalByUserId(Long userId, LocalDate currentTime);

	@Query("select o from orders o where o.id = ?1 and o.userId = ?2 and  o.to < ?3")
	Order findArchivalByUserId(Long id, Long userId, LocalDate now);

}
