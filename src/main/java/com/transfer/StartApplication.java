package com.transfer;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.transfer.domain.Account;
import com.transfer.repository.AccountRepository;

/**
 * This is starting point of Transfer service application
 * 
 * @author amit wadhwa
 *
 */
@SpringBootApplication
@EnableConfigurationProperties({ TransactionProperties.class })
public class StartApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
	SpringApplication.run(StartApplication.class, args);
    }

    /**
     * This is used when it is required to have some data setup at application start
     * 
     * @param accountRepository
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner setup(AccountRepository accountRepository) {
	return (args) -> {
	    if (args.length > 0) {
		accountRepository.save(new Account("amit", new BigDecimal(1000)));
		accountRepository.save(new Account("wadhwa", new BigDecimal(1000)));
		LOGGER.info("The sample data has been generated");
	    }
	};
    }
}