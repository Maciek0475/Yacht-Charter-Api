package com.mac2work.userpanel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		user = User.builder()
				.id(1L)
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.role(Role.ADMIN)
				.build();
		
		userRepository.save(user);
	}

	@Test
	final void UserRepository_findByEmail_ReturnUser() {
		Optional<User> user = userRepository.findByEmail(this.user.getEmail());
		
		assertThat(user.get()).isEqualTo(this.user);
	}

}
