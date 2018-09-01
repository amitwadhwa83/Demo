package com.transfer.repository;

import org.springframework.data.repository.CrudRepository;

import com.transfer.domain.Transfer;

public interface TransferRepository extends CrudRepository<Transfer, Long> {

}
