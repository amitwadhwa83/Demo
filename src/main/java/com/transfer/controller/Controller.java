package com.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transfer.domain.Account;
import com.transfer.domain.Transfer;
import com.transfer.service.AccountService;
import com.transfer.service.TransferService;

@RestController
public class Controller {

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

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
	return transferService.doTransfer(transfer.getSourceAccountId(), transfer.getDestAccountId(),
		transfer.getAmount());
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
}