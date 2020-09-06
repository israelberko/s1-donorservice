package org.ssm.demo.donorservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.ssm.demo.donorservice.entity.Person;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>{
	 
}
