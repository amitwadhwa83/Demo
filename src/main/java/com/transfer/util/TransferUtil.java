package com.transfer.util;

import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transfer.domain.Account;
import com.transfer.service.AccountService;

/**
 * Utility class containing commonly used methods across application
 * 
 * @author amit wadhwa
 *
 */
@Component
public class TransferUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferUtil.class);

    private static final String EXPR_AMOUNT = "^\\d+(\\.\\d{1,2})?$";
    private static final String MSG_ACNT_NOT_FOUND = "Account not found";
    private static final String MSG_INVLD_AMNT_VALUE = "Invalid amount value";
    private static final String MSG_INSUFCINT_BAL = "Insufficient balance in source account";
    private static AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accService) {
	accountService = accService;
    }

    public static void validateTransfer(BigDecimal transferAmount, String... accounts) {
	// Validate account
	validateAccount(accounts);

	// Validate amount
	validateAmount(transferAmount);

	// Validate balance
	validateInsfntAccntBalance(accounts[0], transferAmount.doubleValue());
    }

    private static void validateAccount(String... accounts) {
	for (String account : accounts) {
	    if (isEmpty(account) || !accountService.exists(account)) {
		raiseException(MSG_ACNT_NOT_FOUND, account);
	    }
	}
    }

    public static void validateAmount(BigDecimal amount) {
	if (null == amount || !amount.toString().matches(EXPR_AMOUNT)) {
	    raiseException(MSG_INVLD_AMNT_VALUE, null == amount ? null : amount.toString());
	}
    }

    public static void validateInsfntAccntBalance(String account, Double amount) {
	Account sourceAcct = accountService.findOne(account);
	if (sourceAcct.getBalance().doubleValue() < amount) {
	    raiseException(MSG_INSUFCINT_BAL, sourceAcct.getName());
	}
    }

    public static void raiseException(String message, String value) {
	LOGGER.error("Raising exception for message {} and value {}", message, value);
	throw new RuntimeException(String.join(":", message, value));
    }
}
