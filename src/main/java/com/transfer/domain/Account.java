package com.transfer.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private String name;
    private BigDecimal balance;

    public Account() {
	super();
    }

    public Account(String name, BigDecimal balance) {
	super();
	this.name = name;
	this.balance = balance;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public BigDecimal getBalance() {
	return balance;
    }

    public void setBalance(BigDecimal balance) {
	this.balance = balance;
    }
}