package com.transfer.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Transfer {

    @Id
    @GeneratedValue
    private long id;
    private String sourceAccountId;
    private String destAccountId;
    private BigDecimal amount;

    public long getId() {
	return id;
    }

    public Transfer() {
	super();
    }

    public Transfer(String sourceAccountId, String destAccountId, BigDecimal amount) {
	super();
	this.sourceAccountId = sourceAccountId;
	this.destAccountId = destAccountId;
	this.amount = amount;
    }

    public String getSourceAccountId() {
	return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
	this.sourceAccountId = sourceAccountId;
    }

    public String getDestAccountId() {
	return destAccountId;
    }

    public void setDestAccountId(String destAccountId) {
	this.destAccountId = destAccountId;
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
}