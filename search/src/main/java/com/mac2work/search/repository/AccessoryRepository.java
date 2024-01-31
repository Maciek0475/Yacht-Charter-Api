package com.mac2work.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.search.model.Accessory;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
	
}
