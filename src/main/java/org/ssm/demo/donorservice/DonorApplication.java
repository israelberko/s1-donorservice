package org.ssm.demo.donorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.ssm.demo.donorservice.service.DonorOutboxService;

@SpringBootApplication
@EnableJpaAuditing
@Import(DonorOutboxService.class)
public class DonorApplication 
{
	public static void main( String[] args )
    {
    	SpringApplication.run(DonorApplication.class, args);
    }
}
