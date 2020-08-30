package org.ssm.demo.partyservice.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.ssm.demo.accountservice.entity.Account;

@Service
public class AccountCommandHandler {
Logger LOG = LoggerFactory.getLogger(AccountCommandHandler.class);
	
	@KafkaListener(topics = "dbserver1.account.accounts", groupId = "account-consumer")
	public Account suggested(Map<?,?> message) {
		Account account = Account.of(message);
		LOG.info(String.format("Message received: %s", account));
		return account;
	}
}
