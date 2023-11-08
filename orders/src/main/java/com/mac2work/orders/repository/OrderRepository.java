package com.mac2work.orders.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mac2work.orders.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select u from orders u where u.userId = ?1 and u.to > ?2")
	List<Order> findAllActualByUserId(Long userId, LocalDate currentTime);
	
	@Query("select u from orders u where u.id = ?1 and u.userId = ?2 and u.to > ?3")
	Order findActualByUserId(Long id, Long userId, LocalDate currentTime);

	@Query("select u from orders u where u.userId = ?1 and u.to < ?2")
	List<Order> findAllArchivalByUserId(Long userId, LocalDate currentTime);

	@Query("select u from orders u where u.id = ?1 and u.userId = ?2 and  u.to < ?3")
	Order findArchivalByUserId(Long id, Long userId, LocalDate now);
	
	

}
