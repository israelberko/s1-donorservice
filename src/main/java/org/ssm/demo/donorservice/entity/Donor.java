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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Donor implements BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	UUID pledge_id;
	String first_name;
	String last_name;
	String email;
	Integer amount;
	@CreatedDate @Temporal(TemporalType.TIMESTAMP) Date created_at;
	@LastModifiedDate @Temporal(TemporalType.TIMESTAMP) Date updated_at;
	
	public static Donor of(Map<?,?> data) {
		return new Donor().buildFrom(data, Donor.class);
	}
}
