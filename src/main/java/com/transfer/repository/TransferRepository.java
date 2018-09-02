package com.transfer.repository;

import org.springframework.data.repository.CrudRepository;

import com.transfer.domain.Transfer;

/**
 * Repository class for Transfer operations
 * 
 * @author amit wadhwa
 *
 */
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
