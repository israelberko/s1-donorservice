package org.ssm.demo.donorservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ssm.demo.donorservice.entity.Donor;

@Repository
public interface DonorRepository extends PagingAndSortingRepository<Donor, Long>{
	@Query("DELETE u FROM Donor d WHERE d.pledge_id = :pledge_id")
	 List<Donor> deleteByPledgeId( @Param("pledge_id")  UUID pledgeId);
}