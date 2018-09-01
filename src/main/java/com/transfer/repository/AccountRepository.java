package com.transfer.repository;

import org.springframework.data.repository.CrudRepository;

import com.transfer.domain.Account;

public interface AccountRepository extends CrudRepository<Account, String> {
}
