package com.transfer.service;

import static com.transfer.util.TransferUtil.validateAmount;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfer.domain.Account;
import com.transfer.repository.AccountRepository;

@Transactional(rollbackOn = Exception.class)
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private static final Account EMPTY_ACCOUNT = new Account();

    @Override
    public Account findOne(String name) {
	return accountRepository.findById(name).orElse(EMPTY_ACCOUNT);
    }

    @Override
    public boolean exists(String name) {
	return accountRepository.existsById(name);
    }

    @Override
    public void createAccount(String name, BigDecimal balance) {
	validateAmount(balance);
	accountRepository.save(new Account(name, balance));
    }

    @Override
    public Iterable<Account> findAll() {
	return accountRepository.findAll();
    }
}
