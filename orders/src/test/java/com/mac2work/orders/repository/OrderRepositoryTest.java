
package com.mac2work.orders.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mac2work.orders.model.Order;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;
	
	private Order order;
	private Order order2;

	Long userId;
	
	LocalDate from;
	LocalDate to;

	@BeforeEach
	void setUp() throws Exception {
		userId = 1L;
		
		from = LocalDate.of(2024, 4, 15);
		to = LocalDate.of(2024, 4, 25);
		
		order = Order.builder()
				.userId(userId)
				.yachtId(2L)
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
		order2 = Order.builder()
				.userId(userId)
				.yachtId(1L)
				.days(10)
				.dateFrom(from)
				.dateTo(to)
				.price(1722.5)
				.build();
		orderRepository.save(order);
		orderRepository.save(order2);
	}

	@Test
	final void orderRepository_findAllActualByUserId_ReturnListOfOrders() {
		List<Order> expectedOrders = List.of(order, order2);
		
		List<Order> actualOrders = orderRepository.findAllActualByUserId(userId, LocalDate.now());

		assertThat(actualOrders.size()).isEqualTo(expectedOrders.size());
	}


	@Test
	final void orderRepository_findActualByUserId_ReturnOrder() {
		Order order = orderRepository.findActualByUserId(this.order.getId(), userId, LocalDate.now());
		
		assertThat(order).isEqualTo(this.order);
	}

	@Test
	final void orderRepository_findAllArchivalByUserId_ReturnListOfOrders() {
		order.setDateFrom(LocalDate.of(2023, 4, 15));
		order.setDateTo(LocalDate.of(2023, 4, 25));
		order2.setDateFrom(LocalDate.of(2023, 4, 15));
		order2.setDateTo(LocalDate.of(2023, 4, 25));
		List<Order> expectedOrders = List.of(order, order2);
		
		List<Order> actualOrders = orderRepository.findAllArchivalByUserId(userId, LocalDate.now());

		assertThat(actualOrders.size()).isEqualTo(expectedOrders.size());
	}

	@Test
	final void orderRepository_findArchivalByUserId_ReturnOrder() {
		order.setDateFrom(LocalDate.of(2023, 4, 15));
		order.setDateTo(LocalDate.of(2023, 4, 25));
		Order order = orderRepository.findArchivalByUserId(this.order.getId(), userId, LocalDate.now());
		
		assertThat(order).isEqualTo(this.order);
	}

}
