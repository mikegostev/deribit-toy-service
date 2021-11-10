package co.copper.deribitsvc.model;

import java.math.BigDecimal;

public record CurrencyInfo(
		BigDecimal balance,
		BigDecimal available) {
}
