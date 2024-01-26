package com.mac2work.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.search.model.Propulsion;
import com.mac2work.search.model.Yacht;

@Repository
public interface YachtRepository extends JpaRepository<Yacht, Long>{

	List<Yacht> findAllByPropulsion(Propulsion propulsion);

}
