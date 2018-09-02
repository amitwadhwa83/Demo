package com.transfer.repository;

import org.springframework.data.repository.CrudRepository;

import com.transfer.domain.Account;

/**
 * Repository class for Account operations
 * 
 * @author amit wadhwa
 *
 */
public interface AccountRepository extends CrudRepository<Account, String> {
}
