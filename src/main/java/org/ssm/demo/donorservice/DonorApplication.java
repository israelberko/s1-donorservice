package org.ssm.demo.donorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DonorApplication 
{
	public static void main( String[] args )
    {
    	SpringApplication.run(DonorApplication.class, args);
    }
}
