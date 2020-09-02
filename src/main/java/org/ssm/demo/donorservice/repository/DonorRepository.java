package org.ssm.demo.donorservice.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ssm.demo.donorservice.entity.Donor;

@Repository
public interface DonorRepository extends CrudRepository<Donor, UUID>{
	 
}
