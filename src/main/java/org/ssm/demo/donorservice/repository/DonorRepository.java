package org.ssm.demo.donorservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.ssm.demo.donorservice.entity.Donor;

@Repository
public interface DonorRepository extends PagingAndSortingRepository<Donor, Long>{
	
	List<Donor> deleteByPledgeId(UUID eventId);
	 
}
