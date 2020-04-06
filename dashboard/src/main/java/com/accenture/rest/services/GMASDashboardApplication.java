package com.accenture.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.accenture.gmas.repo")
@EntityScan("com.accenture.dao.entity")
public class GMASDashboardApplication extends SpringBootServletInitializer implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(GMASDashboardApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(GMASDashboardApplication.class, args);
	}

	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GMASDashboardApplication.class);
	}

	public void run(ApplicationArguments arg0) throws Exception {
		System.out.println("BootStartup complete....");
	}

	

	

}
