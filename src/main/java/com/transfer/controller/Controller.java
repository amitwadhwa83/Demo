package com.transfer.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.transfer.domain.Account;
import com.transfer.domain.Transfer;
import com.transfer.service.AccountService;
import com.transfer.service.TransferService;

/**
 * This is controller class for Transfer service
 * 
 * @author amit wadhwa
 *
 */
@RestController
public class Controller {

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

    private static final String DLMTR_ERR_MSG = ":";
    private static final String DLMTR_LINE_BREAK = "\n";

    @RequestMapping("/")
    public String sayHello() {
	return "Welcome to Transfer Service";
    }

    // Transfer services
    @RequestMapping(value = "/transfers", method = RequestMethod.GET)
    public Iterable<Transfer> getTransfers() {
	return transferService.findAll();
    }

    @RequestMapping(value = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable("id") long id) {
	return transferService.findOne(id);
    }

    @RequestMapping(value = "/transfer/create", method = RequestMethod.POST)
    public long doTransfer(@RequestBody Transfer transfer) {
	return transferService.doTransfer(transfer.getSourceAccount(), transfer.getDestAccount(), transfer.getAmount());
    }

    // Account services
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public Iterable<Account> getAccounts() {
	return accountService.findAll();
    }

    @RequestMapping(value = "/account/{name}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable("name") String name) {
	return accountService.findOne(name);
    }

    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public String createAccount(@RequestBody Account account) {
	accountService.createAccount(account.getName(), account.getBalance());
	return "Success";
    }

    // Exception handler
    @SuppressWarnings("rawtypes")
    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
	Throwable cause = ((TransactionSystemException) ex).getRootCause();

	// If ConstraintViolationException then enrich the message
	if (cause instanceof ConstraintViolationException) {
	    StringBuilder errorMsg = new StringBuilder();
	    for (ConstraintViolation constraintViolation : ((ConstraintViolationException) cause)
		    .getConstraintViolations()) {
		errorMsg.append(String.join(DLMTR_ERR_MSG, constraintViolation.getPropertyPath().toString(),
			constraintViolation.getMessage())).append(DLMTR_LINE_BREAK);
	    }
	    return new ResponseEntity<>(errorMsg.toString(), HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}