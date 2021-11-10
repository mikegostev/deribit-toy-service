package co.copper.deribitsvc.model;

import java.math.BigDecimal;

public class Transfer {
	private BigDecimal amount;
	private long created_timestamp;
	private String currency;
	private String direction;
	private long id;
	private String other_side;
	private TransferState state;
	private String type;
	private long updated_timestamp;

	public BigDecimal getAmount() {
		return amount;
	}

	public long getCreated_timestamp() {
		return created_timestamp;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDirection() {
		return direction;
	}

	public long getId() {
		return id;
	}

	public String getOther_side() {
		return other_side;
	}

	public TransferState getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public long getUpdated_timestamp() {
		return updated_timestamp;
	}
}
