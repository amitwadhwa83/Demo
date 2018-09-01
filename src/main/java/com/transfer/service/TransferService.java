package com.transfer.service;

import java.math.BigDecimal;

import com.transfer.domain.Transfer;

public interface TransferService {

    Transfer findOne(long id);

    Iterable<Transfer> findAll();

    long doTransfer(String sourceAccountName,String destAccountName,BigDecimal amount);
}
