package com.transfer.service;

import static com.transfer.util.TransferUtil.raiseException;
import static com.transfer.util.TransferUtil.validateAmount;
import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfer.domain.Account;
import com.transfer.repository.AccountRepository;

/**
 * Service class for Account operations
 * 
 * @author amit wadhwa
 *
 */
@Service("accountService")
@Transactional(rollbackOn = Exception.class)
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    private static final Account EMPTY_ACCOUNT = new Account();

    @Override
    public Account findOne(String name) {
	checkEmpty(name);
	return accountRepository.findById(name).orElse(EMPTY_ACCOUNT);
    }

    @Override
    public boolean exists(String name) {
	checkEmpty(name);
	return accountRepository.existsById(name);
    }

    @Override
    public void createAccount(String name, BigDecimal balance) {
	checkEmpty(name);
	validateAmount(balance);
	accountRepository.save(new Account(name, balance));
	LOGGER.info("Created account {} with balance {}", name, balance);
    }

    @Override
    public Iterable<Account> findAll() {
	return accountRepository.findAll();
    }

    private static void checkEmpty(String value) {
	if (isEmpty(value)) {
	    raiseException("Invalid input", value);
	}
    }
}