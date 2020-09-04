package org.ssm.demo.donorservice.service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
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
	
	private static final String REQUEST_PLEDGE = "REQUEST_PLEDGE";
	
	@Transactional
	@KafkaListener(topics = "dbserver1.pledge.pledge_outbox", groupId = "donor-consumer")
	public void pledgeRequested(Map<?,?> message) {
		DonorOutbox outbox = DonorOutbox.of(message);
		if (REQUEST_PLEDGE.equals(outbox.getEvent_type())) {
			applicationEventPublisher.publishEvent(new SendOutboxEvent(outbox));
			saveRandomDonor(outbox);
		}
			
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void acceptOutboxEvent(SendOutboxEvent event){
		LOG.info("Donor Outbox: {}", event.getOutbox());
		donorOutboxRepository.save(event.getOutbox());
		donorOutboxRepository.delete(event.getOutbox());
	}
	
	public void saveRandomDonor(DonorOutbox outbox) {
		UUID pledgeId = outbox.getEvent_id();
		LOG.info("Received {} msg for Pledge ID {}", outbox.getEvent_type(), pledgeId);
		Donor donor = getRandomDonor(pledgeId);
		donor.setAmount(getRandomAmount());
		donor.setPledge_id(pledgeId);
		donorRepository.save(donor);
	}
	
	private Donor getRandomDonor(UUID pledge_id) {
		Integer randomDonor = new Random().nextInt(199);
		Page<Donor> donor = donorRepository.findAll(PageRequest.of(randomDonor, 1));
		return donor.isEmpty() ? new Donor() : donor.toList().get(0);
	}
	
	private Integer getRandomAmount() {
		Integer randomAmount = new Random().nextInt(10);
		return randomAmount;
	}
}
