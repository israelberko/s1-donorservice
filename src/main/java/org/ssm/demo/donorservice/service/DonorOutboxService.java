package org.ssm.demo.donorservice.service;

import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.ssm.demo.donorservice.entity.Donor;
import org.ssm.demo.donorservice.entity.DonorOutbox;
import org.ssm.demo.donorservice.repository.DonorOutboxRepository;

@Service
public class DonorOutboxService {
	
	private static Logger LOG = LoggerFactory.getLogger(DonorOutboxService.class);
	@Autowired ApplicationEventPublisher applicationEventPublisher;
	@Autowired DonorService donorService;
	@Autowired DonorOutboxRepository donorOutboxRepository;
	
//	@Transactional
//	@KafkaListener(topics = "donor.inbox", groupId = "donor-consumer")
//	public void pledgeRequested(DonorOutbox message) {
//		LOG.info("In donor service: {}", message);
//		
//		Donor donorToSave = donorService.getRandomDonor(message.getEvent_id());
//		
//		applicationEventPublisher.publishEvent(DonorOutbox.from(donorToSave));
//		
//		donorService.save(donorToSave);
//			
//	}
	
	@Transactional
	public Consumer<DonorOutbox> pledgeRequested() {
		return message -> {
			LOG.info("In donor service: {}", message);
			
			Donor donorToSave = donorService.getRandomDonor(message.getEvent_id());
			
			applicationEventPublisher.publishEvent(DonorOutbox.from(donorToSave));
			
			donorService.save(donorToSave);
		};	
	}
	
//	@Transactional
//	@KafkaListener(topics = "donor.cancel.inbox", groupId = "donor-consumer")
//	public void pledgeCancelRequested(DonorOutbox message) {
//		LOG.info("In donor service for cancellation: {}", message);
//		
//		UUID pledgeId = message.getEvent_id();
//		
//		boolean deleted = donorService.deleteDonorsByPledgeId(pledgeId);
//		
//		message.setEvent_type(deleted ? 
//				"PLEDGE_CANCEL_REQUESTED_ACK" :
//					"PLEDGE_CANCEL_REQUESTED_NACK");
//		
//		applicationEventPublisher.publishEvent(message);
//		
//		LOG.info("Records deleted: {}", deleted);
//			
//	}
	
	@Transactional
	public Consumer<DonorOutbox> pledgeCancelRequested() {
		return message -> {
			LOG.info("In donor service for cancellation: {}", message);
			
			UUID pledgeId = message.getEvent_id();
			
			boolean deleted = donorService.deleteDonorsByPledgeId(pledgeId);
			
			message.setEvent_type(deleted ? 
					"PLEDGE_CANCEL_REQUESTED_ACK" :
						"PLEDGE_CANCEL_REQUESTED_NACK");
			
			applicationEventPublisher.publishEvent(message);
			
			LOG.info("Records deleted: {}", deleted);
		};
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void acceptOutboxEvent(DonorOutbox outbox){
		LOG.info("Donor Outbox: {}", outbox);
		
		donorOutboxRepository.save(outbox);
		
		donorOutboxRepository.delete(outbox);
	}
}
