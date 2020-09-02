package org.ssm.demo.donorservice.service;

import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.ssm.demo.donorservice.applicationevents.SendOutboxEvent;
import org.ssm.demo.donorservice.entity.Donor;
import org.ssm.demo.donorservice.entity.DonorOutbox;
import org.ssm.demo.donorservice.repository.DonorOutboxRepository;
import org.ssm.demo.donorservice.repository.DonorRepository;

@Service
public class DonorOutboxService {
	
	private static Logger LOG = LoggerFactory.getLogger(DonorOutboxService.class);
	@Autowired DonorOutboxRepository donorOutboxRepository;
	@Autowired ApplicationEventPublisher applicationEventPublisher;
	@Autowired DonorRepository donorRepository;
	
	@Transactional
	@Bean
	public Consumer<Map<?,?>> createDonorOutbox() {
		return message -> {
			Donor donor = Donor.of(message);
			DonorOutbox donorOutbox = DonorOutbox.from(donor);
			applicationEventPublisher.publishEvent(new SendOutboxEvent(donorOutbox));
		};
	}
	

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void acceptOutboxEvent(SendOutboxEvent event){
		LOG.info("Outbox: {}", event.getOutbox());
		donorOutboxRepository.save(event.getOutbox());
		donorOutboxRepository.delete(event.getOutbox());
	}
	
	@Transactional
	@Bean
	public Consumer<Map<?,?>> donationRequested() {
		return message -> {
			DonorOutbox donationRequested = DonorOutbox.of(message);
			LOG.info("DonorOutbox: {}", donationRequested);
		
		};
	}
}
