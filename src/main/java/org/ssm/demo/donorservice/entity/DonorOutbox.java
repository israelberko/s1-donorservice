package org.ssm.demo.donorservice.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.ssm.demo.donorservice.shared.BaseEntity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class DonorOutbox implements BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	UUID event_id;
	String event_type;
	String payload;
	@CreatedDate @Temporal(TemporalType.TIMESTAMP) Date created_at;
	@LastModifiedDate @Temporal(TemporalType.TIMESTAMP) Date updated_at;
	
	public static DonorOutbox of(Map<?,?> data) {
		return new DonorOutbox().buildFrom(data, DonorOutbox.class);
	}
	
	public static DonorOutbox from(Donor donor) {
		DonorOutbox outbox = new DonorOutbox();
		
		outbox.setEvent_id(donor.getPledge_id());
		
		outbox.setEvent_type("PLEDGE_REQUESTED_ACK");
		
		outbox.setPayload(donor.toMap().toString());
		
		return outbox;
	}
}


