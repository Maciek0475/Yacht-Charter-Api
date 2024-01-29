package com.mac2work.search.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mac2work.search.model.Accessory;
import com.mac2work.search.model.Propulsion;
import com.mac2work.search.model.Yacht;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class YachtRepositoryTest {
	
	@Autowired
	private YachtRepository yachtRepository;
	@Autowired
	private AccessoryRepository accessoryRepository;
	
	private Yacht yacht;
	private Yacht yacht2;
	
	private Accessory accessory;
	private Accessory accessory2;
	

	@BeforeEach
	void setUp() throws Exception {
		accessory = Accessory.builder()
				.name("tent")
				.build();
		accessory2 = Accessory.builder()
				.name("sink")
				.build();
		yacht = Yacht.builder()
				.model("Sasanka")
				.propulsion(Propulsion.SAILING)
				.length(6.60)
				.capacity(5)
				.motorPower(8.0)
				.priceFrom(150.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		yacht2 = Yacht.builder()
				.model("Orion")
				.propulsion(Propulsion.SAILING)
				.length(5.93)
				.capacity(5)
				.motorPower(5.0)
				.priceFrom(130.0)
				.accessories(List.of(accessory, accessory2))
				.build();
		
		accessoryRepository.save(accessory);
		accessoryRepository.save(accessory2);
		
		yachtRepository.save(yacht);
		yachtRepository.save(yacht2);
	}

	@Test
	final void yachtRepository_findAllByPropulsion_ReturnMoreThanOneYacht() {
		Propulsion propulsion = Propulsion.SAILING;
		
		List<Yacht> yachts = yachtRepository.findAllByPropulsion(propulsion);

		assertThat(yachts.size()).isGreaterThan(1);
	}

}
