package com.transfer.util;

import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import com.transfer.domain.Account;
import com.transfer.service.AccountService;

public class TransferUtil {

    private static AccountService accountService;

    @PostConstruct
    public void setAccountService(AccountService accountService) {
	TransferUtil.accountService = accountService;
    }

    private static final String EXPR_AMOUNT = "[0-9]+([.][0-9]{1,2})?";

    public static void validate(BigDecimal amount, String... accounts) {
	// Validate account
	validateAccount(accounts);

	// Validate amount
	validateAmount(amount);

	// Validate balance
	Account sourceAcct = accountService.findOne(accounts[0]);
	if (sourceAcct.getBalance().doubleValue() < amount.doubleValue()) {
	    throw new RuntimeException("Insufficient balance in source account:" + sourceAcct.getName());
	}
    }

    private static void validateAccount(String... accounts) {
	for (String account : accounts) {
	    if (isEmpty(account) || !accountService.exists(account)) {
		throw new RuntimeException("Account not found:" + account);
	    }
	}
    }

    public static void validateAmount(BigDecimal amount) {
	if (!amount.toString().matches(EXPR_AMOUNT)) {
	    throw new RuntimeException("Invalid amount value:" + amount);
	}
    }
}
