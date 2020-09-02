package org.ssm.demo.donorservice.applicationevents;

import org.ssm.demo.donorservice.entity.DonorOutbox;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SendOutboxEvent {
	DonorOutbox outbox;
}
