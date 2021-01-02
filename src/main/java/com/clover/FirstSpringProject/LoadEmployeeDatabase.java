package com.clover.FirstSpringProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.clover.AccessingDataJpa.EmployeeRepository;
import com.clover.Employee.Employee;

@Configuration // tags the class as a source of bean definitions for the application context
@EntityScan(basePackages = { "com.clover.Employee" })
@ComponentScan(basePackages = "com.clover.ModelAssembler")
@EnableJpaRepositories(basePackages = "com.clover.AccessingDataJpa")
public class LoadEmployeeDatabase {
	
	private static final Logger log = LoggerFactory.getLogger(LoadEmployeeDatabase.class);
	
	@Bean // Spring Boot will run ALL CommandLineRunner beans once the application context is loaded
	CommandLineRunner initDatabase(EmployeeRepository repository) { // this runner will request a copy of EmployeeRepository
		return args -> {
	      log.info("Preloading " + repository.save(new Employee("Zihan", "Ye"))); // create an entity and store them
	      log.info("Preloading " + repository.save(new Employee("Clover", "Ye")));
	    };
	}
}
