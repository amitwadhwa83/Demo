package com.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.transfer.domain.Transfer;
import com.transfer.repository.TransferRepository;

@SpringBootApplication
@EnableConfigurationProperties({ TransactionProperties.class })
public class StartApplication {

    private static final Logger logger = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
	SpringApplication.run(StartApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner setup(TransferRepository transferRepository) {
	return (args) -> {
	    transferRepository.save(new Transfer("Gustavo", "Ponce", true));
	    transferRepository.save(new Transfer("John", "Smith", true));
	    transferRepository.save(new Transfer("Jim ", "Morrison", false));
	    transferRepository.save(new Transfer("David", "Gilmour", true));
	    logger.info("The sample data has been generated");
	};
    }*/
}