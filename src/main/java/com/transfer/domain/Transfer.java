package com.transfer.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Domain entity for Transfer
 * 
 * @author amit wadhwa
 *
 */
@Entity
// @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Transfer {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String sourceAccount;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String destAccount;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    public long getId() {
	return id;
    }

    public Transfer() {
	super();
    }

    public Transfer(String sourceAccount, String destAccount, BigDecimal amount) {
	super();
	this.sourceAccount = sourceAccount;
	this.destAccount = destAccount;
	this.amount = amount;
    }

    public String getSourceAccount() {
	return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
	this.sourceAccount = sourceAccount;
    }

    public String getDestAccount() {
	return destAccount;
    }

    public void setDestAccount(String destAccount) {
	this.destAccount = destAccount;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((amount == null) ? 0 : amount.hashCode());
	result = prime * result + ((destAccount == null) ? 0 : destAccount.hashCode());
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((sourceAccount == null) ? 0 : sourceAccount.hashCode());
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
	Transfer other = (Transfer) obj;
	if (amount == null) {
	    if (other.amount != null)
		return false;
	} else if (!amount.equals(other.amount))
	    return false;
	if (destAccount == null) {
	    if (other.destAccount != null)
		return false;
	} else if (!destAccount.equals(other.destAccount))
	    return false;
	if (id != other.id)
	    return false;
	if (sourceAccount == null) {
	    if (other.sourceAccount != null)
		return false;
	} else if (!sourceAccount.equals(other.sourceAccount))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Transfer [id=" + id + ", sourceAccount=" + sourceAccount + ", destAccount=" + destAccount + ", amount="
		+ amount + "]";
    }
}