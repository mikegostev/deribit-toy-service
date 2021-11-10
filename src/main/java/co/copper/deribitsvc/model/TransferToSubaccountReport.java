package co.copper.deribitsvc.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransferToSubaccountReport (
	long updated_timestamp,
	String type,
	TransferState state,
	int source,
	String other_side,
	long id,
	String direction,
	String currency,
	long created_timestamp,
	BigDecimal amount) {
}

