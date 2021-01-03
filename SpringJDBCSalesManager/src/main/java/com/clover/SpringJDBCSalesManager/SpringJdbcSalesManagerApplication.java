package com.clover.SpringJDBCSalesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJdbcSalesManagerApplication implements CommandLineRunner {

	@Autowired
    private SalesDAO dao;
	
	private static final Logger log = LoggerFactory.getLogger(SpringJdbcSalesManagerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcSalesManagerApplication.class, args);
	}
	
	@Override
	public void run(String... strings) throws Exception {
		log.info("Creating tables");
		dao.initTable();
	}

}
