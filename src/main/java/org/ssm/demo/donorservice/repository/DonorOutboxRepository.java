package org.ssm.demo.donorservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ssm.demo.donorservice.entity.DonorOutbox;

@Repository
public interface DonorOutboxRepository extends CrudRepository<DonorOutbox, Long>{
	 
}
