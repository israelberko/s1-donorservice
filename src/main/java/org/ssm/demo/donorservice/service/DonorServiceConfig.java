package org.ssm.demo.donorservice.service;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.ssm.demo.donorservice.entity.DonorOutbox;

@Configuration
public class DonorServiceConfig {
	@Autowired DonorOutboxService donorOutboxService;
	
	@Transactional
	@Bean
	public Consumer<DonorOutbox> pledgeRequested() {
		return donorOutboxService.pledgeRequested();
	}
	
	@Transactional
	@Bean
	public Consumer<DonorOutbox> pledgeCancelRequested() {
		return donorOutboxService.pledgeCancelRequested();
	}
}
