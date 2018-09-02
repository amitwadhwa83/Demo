package com.transfer.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Domain entity for Account
 * 
 * @author amit wadhwa
 *
 */
@Entity
public class Account {

    @Id
    @NotEmpty
    @Size(min = 0, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String name;

    @Digits(integer = 6, fraction = 2)
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((balance == null) ? 0 : balance.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Account other = (Account) obj;
	if (balance == null) {
	    if (other.balance != null)
		return false;
	} else if (!balance.equals(other.balance))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Account [name=" + name + ", balance=" + balance + "]";
    }
}