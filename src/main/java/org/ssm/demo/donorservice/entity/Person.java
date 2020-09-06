package org.ssm.demo.donorservice.entity;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Person implements BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	String first_name;
	String last_name;
	String email;
	Timestamp createdAt;
	
	public static Person of(Map<?,?> data) {
		return new Person().buildFrom(data, Person.class);
	}
}
