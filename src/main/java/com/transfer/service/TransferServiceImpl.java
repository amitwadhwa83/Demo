package com.transfer.service;

import static com.transfer.util.TransferUtil.validate;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfer.domain.Account;
import com.transfer.domain.Transfer;
import com.transfer.repository.TransferRepository;

@Service("employeeService")
@Transactional(rollbackOn = Exception.class)
public class TransferServiceImpl implements TransferService {

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

	// Validate values
	validate(amount, sourceAccountName, destAccountName);

	// Transfer
	Account source = accountService.findOne(sourceAccountName);
	Account destination = accountService.findOne(destAccountName);
	source.getBalance().subtract(amount);
	destination.getBalance().add(amount);

	// Update transaction
	return transferRepository.save(new Transfer(sourceAccountName, destAccountName, amount)).getId();
    }
}