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
public class Donor implements BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	UUID pledge_id;
	String first_name;
	String last_name;
	String email;
	Integer amount;
	Timestamp createdAt;
	
	public static Donor of(Map<?,?> data) {
		return new Donor().buildFrom(data, Donor.class);
	}
}
