package org.ssm.demo.donorservice.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssm.demo.donorservice.entity.Donor;
import org.ssm.demo.donorservice.entity.Person;
import org.ssm.demo.donorservice.repository.DonorRepository;
import org.ssm.demo.donorservice.repository.PersonRepository;

@Service
public class DonorService {

	@Autowired DonorRepository donorRepository;
	@Autowired PersonRepository personRepository;
	
	@Transactional
	public Donor getRandomDonor(UUID pledge_id) {
		Integer index = new Random().nextInt(30);
		
		Integer amount = new Random().nextInt(10);
		
		Page<Person> personSet = personRepository.findAll(PageRequest.of(index, 1));
		
		Person person = personSet.isEmpty() ? new Person() : personSet.toList().get(0);
		
		Donor donor = Donor.builder()
						.amount(amount)
						.pledge_id(pledge_id)
						.first_name(person.getFirst_name())
						.last_name(person.getLast_name())
						.email(person.getEmail())
					.build();
		
		return donor;
	}
	
	@Transactional
	public boolean deleteDonorsByPledgeId(UUID pledgeId) {
		int deleted = donorRepository.deleteByPledgeId(pledgeId);
		return ( deleted > 0 );
	}
	
	@Transactional
	public void save(Donor donor) {
		donorRepository.save(donor);
	}
}
