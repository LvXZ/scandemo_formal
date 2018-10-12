package com.njfu.entity;

import java.math.BigDecimal;

public class InvInventory {
    private String item;

	private String whId;

	private String locaId;

	private BigDecimal available;

	private BigDecimal quantity;

	private BigDecimal assignQuantity;

	private BigDecimal blockedQuantity;

	private String lotNumber;

	private String serialNumber;

	private String itemSpec;

	private BigDecimal copr;

	public InvInventory(String item, String whId, String locaId, String itemSpec) {
		this.item = item;
		this.whId = whId;
		this.locaId = locaId;
		this.itemSpec = itemSpec;
	}

	public BigDecimal getAvailable() {
		return available;
	}

	public void setAvailable(BigDecimal available) {
		this.available = available;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getLocaId() {
		return locaId;
	}

	public void setLocaId(String locaId) {
		this.locaId = locaId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAssignQuantity() {
		return assignQuantity;
	}

	public void setAssignQuantity(BigDecimal assignQuantity) {
		this.assignQuantity = assignQuantity;
	}

	public BigDecimal getBlockedQuantity() {
		return blockedQuantity;
	}

	public void setBlockedQuantity(BigDecimal blockedQuantity) {
		this.blockedQuantity = blockedQuantity;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public BigDecimal getCopr() {
		return copr;
	}

	public void setCopr(BigDecimal copr) {
		this.copr = copr;
	}

	
}