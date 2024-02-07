package com.mac2work.userpanel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.userpanel.model.Role;
import com.mac2work.userpanel.model.User;
import com.mac2work.userpanel.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
	
	@Mock
	private JwtService jwtService;
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private AuthorizationService authorizationService;
	
	private User user;
	private String token;
	private Long id;

	@BeforeEach
	void setUp() throws Exception {
		id = 1L;
		
		user = User.builder()
				.id(id)
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("maciekjurczak123@gmail.com")
				.password("P@ssword123")
				.role(Role.ADMIN)
				.build();
		token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

	}

	@Test
	final void authorizationService_isAdmin_ReturnTrue() {
		when(jwtService.extractUsername(token.substring(7))).thenReturn(user.getUsername());
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		
		Boolean isAdmin = authorizationService.isAdmin(token, "/path", "GET");
		
		assertThat(isAdmin).isTrue();
	}

	@Test
	final void authorizationService_isCorrectUser_ReturnTrue() {
		AuthorizationService authorizationServiceSpy = spy(authorizationService);
		doReturn(id).when(authorizationServiceSpy).getLoggedInUserId(token);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		
		Boolean isCorrectUser = authorizationServiceSpy.isCorrectUser(token, id, "Resource");
		
		assertThat(isCorrectUser).isTrue();
	}

	@Test
	final void authorizationService_getLoggedInUserId_ReturnId() {
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		when(jwtService.extractUsername(token.substring(7))).thenReturn(user.getUsername());	

		Long id = authorizationService.getLoggedInUserId(token);
		
		assertThat(id).isEqualTo(id);
	}
		
		
}
