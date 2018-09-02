package com.transfer.service;

import static com.transfer.util.TransferUtil.validateTransfer;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfer.domain.Account;
import com.transfer.domain.Transfer;
import com.transfer.repository.TransferRepository;

/**
 * Service class for Transfer operations
 * 
 * @author amit wadhwa
 *
 */
@Service("transferService")
@Transactional(rollbackOn = Exception.class)
public class TransferServiceImpl implements TransferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountService accountService;
    private static final Transfer EMPTY_TRANSFER = new Transfer();

    @Override
    public Transfer findOne(long id) {
	return transferRepository.findById(id).orElse(EMPTY_TRANSFER);
    }

    @Override
    public Iterable<Transfer> findAll() {
	return transferRepository.findAll();
    }

    @Override
    public long doTransfer(String sourceAccountName, String destAccountName, BigDecimal amount) {

	LOGGER.info("Initiating the transfer for amount {} from account {} to account {}", amount, sourceAccountName,
		destAccountName);

	// Validate values
	validateTransfer(amount, sourceAccountName, destAccountName);

	// Do transfer
	Account source = accountService.findOne(sourceAccountName);
	Account destination = accountService.findOne(destAccountName);
	source.setBalance(source.getBalance().subtract(amount));
	destination.setBalance(destination.getBalance().add(amount));

	// Record transaction
	return transferRepository.save(new Transfer(sourceAccountName, destAccountName, amount)).getId();
    }
}