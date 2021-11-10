package co.copper.deribitsvc.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WithdrawReport(
		String address,
		BigDecimal amount,
		long confirmed_timestamp,
		long created_timestamp,
		String currency,
		BigDecimal fee,
		long id,
		BigDecimal priority,
		TransferState state,
		String transaction_id,
		long updated_timestamp) {
}
