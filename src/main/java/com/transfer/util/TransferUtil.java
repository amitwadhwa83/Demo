package com.transfer.util;

import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.transfer.service.AccountService;

/**
 * Utility class containing commonly used methods across application
 * 
 * @author amit wadhwa
 *
 */
public class TransferUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferUtil.class);

    private static final String EXPR_AMOUNT = "^\\d+(\\.\\d{1,2})?$";
    private static final String MSG_ACNT_NOT_FOUND = "Account not found";
    private static final String MSG_INVLD_AMNT_VALUE = "Invalid amount value";

    public static void validateTransfer(AccountService accountService, BigDecimal transferAmount, String... accounts) {
	// Validate account
	for (String account : accounts) {
	    if (isEmpty(account) || !accountService.exists(account)) {
		raiseException(MSG_ACNT_NOT_FOUND, account);
	    }
	}
	// Validate amount
	validateAmount(transferAmount);
    }

    public static void validateAmount(BigDecimal amount) {
	if (null == amount || !amount.toString().matches(EXPR_AMOUNT)) {
	    raiseException(MSG_INVLD_AMNT_VALUE, null == amount ? null : amount.toString());
	}
    }

    public static void raiseException(String message, String value) {
	LOGGER.error("Raising exception for message {} and value {}", message, value);
	throw new RuntimeException(String.join(":", message, value));
    }
}
