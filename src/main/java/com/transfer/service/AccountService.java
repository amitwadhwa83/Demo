package com.transfer.service;

import java.math.BigDecimal;

import com.transfer.domain.Account;

public interface AccountService {
    Account findOne(String name);

    Iterable<Account> findAll();

    boolean exists(String name);

    void createAccount(String name, BigDecimal balance);
}
