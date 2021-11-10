package co.copper.deribitsvc.connector.deribit;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSetter;

public class AssetInfo {
	private String currency;
	
	private BigDecimal balance;
	
	private BigDecimal equity;

	@JsonSetter("available_funds")
	private BigDecimal availableFunds;

	@JsonSetter("available_withdrawal_funds")
	private BigDecimal availableWithdrawalFunds;

	@JsonSetter("initial_margin")
	private BigDecimal initialMargin;

	@JsonSetter("maintenance_margin")
	private BigDecimal maintenanceMargin;

	@JsonSetter("margin_balance")
	private BigDecimal marginBalance;

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getAvailableFunds() {
		return availableFunds;
	}

	public BigDecimal getAvailableWithdrawalFunds() {
		return availableWithdrawalFunds;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getEquity() {
		return equity;
	}

	public BigDecimal getInitialMargin() {
		return initialMargin;
	}

	public BigDecimal getMaintenanceMargin() {
		return maintenanceMargin;
	}
}
