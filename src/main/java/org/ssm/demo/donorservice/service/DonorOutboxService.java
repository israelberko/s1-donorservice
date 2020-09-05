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
	
	@Transactional
	@KafkaListener(topics = "donor.inbox", groupId = "donor-consumer")
	public void pledgeRequested(Map<?,?> record) {
		DonorOutbox message = DonorOutbox.of(record);
		LOG.info("In donor service - {}", message);
		
		Donor donorToSave = getRandomDonor(message.getEvent_id());
		DonorOutbox outbox = DonorOutbox.from(donorToSave);
		applicationEventPublisher.publishEvent(new SendOutboxEvent(outbox));
		donorRepository.save(donorToSave);
			
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void acceptOutboxEvent(SendOutboxEvent event){
		LOG.info("Donor Outbox: {}", event.getOutbox());
		donorOutboxRepository.save(event.getOutbox());
		donorOutboxRepository.delete(event.getOutbox());
	}
	
	private Donor getRandomDonor(UUID pledge_id) {
		Integer randomDonorIndex = new Random().nextInt(199);
		Page<Donor> donorSet = donorRepository.findAll(PageRequest.of(randomDonorIndex, 1));
		Donor randomDonor = donorSet.isEmpty() ? new Donor() : donorSet.toList().get(0);
		randomDonor.setAmount(getRandomAmount());
		randomDonor.setPledge_id(pledge_id);
		return randomDonor;
	}
	
	private Integer getRandomAmount() {
		Integer randomAmount = new Random().nextInt(10);
		return randomAmount;
	}
}
