package org.ssm.demo.donorservice.entity;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
public class DonorOutbox implements BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	UUID event_id;
	String event_type;
	String payload;
	Timestamp created_at;
	
	public static DonorOutbox of(Map<?,?> data) {
		return new DonorOutbox().buildFrom(data, DonorOutbox.class);
	}
	
	public static DonorOutbox from(Donor donor) {
		DonorOutbox outbox = new DonorOutbox();
		outbox.setEvent_id(donor.getPledge_id());
		outbox.setEvent_type("MATCH_PLEDGE");
		outbox.setPayload(donor.toMap().toString());
		return outbox;
	}
}


