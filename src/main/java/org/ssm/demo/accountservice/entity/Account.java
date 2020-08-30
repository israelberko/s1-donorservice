package org.ssm.demo.accountservice.entity;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Account implements Entity{
	Long id;
	UUID party_id;
	Long pledge;
	Timestamp createdAt;
	
	public static Account of(Map<?,?> data) {
		return new Account().buildFrom(data, Account.class);
	}
}
