package co.copper.deribitsvc.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Deposit(
		String address,
		BigDecimal amount,
		String currency,
		TransferState state,
		String transaction_id,
		long received_timestamp,
		long updated_timestamp) {
}
