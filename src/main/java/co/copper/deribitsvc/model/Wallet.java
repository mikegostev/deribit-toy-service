package co.copper.deribitsvc.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Wallet {
	private Map<String, CurrencyInfo> wallet = new HashMap<>();

	@JsonGetter("wallet")
	public Map<String, CurrencyInfo> getWalletAsMap() {
		return wallet;
	}

	public void setCurrencyInfo(String currencyKey, CurrencyInfo cInfo) {
		wallet.put(currencyKey, cInfo);
	}

	@JsonIgnore
	public CurrencyInfo getCurrencyInfo(String currencyKey) {
		return wallet.computeIfAbsent(currencyKey, k -> new CurrencyInfo(BigDecimal.ZERO,BigDecimal.ZERO));
	}

	public void merge(String currencyKey, CurrencyInfo cInfo) {
		CurrencyInfo existingInfo = wallet.get(currencyKey);

		if (!wallet.containsKey(currencyKey)) {
			wallet.put(currencyKey, cInfo);
		} else {
			wallet.put(currencyKey, new CurrencyInfo(
					existingInfo.balance().add(cInfo.balance()),
					existingInfo.available().add(cInfo.available())));
		}
	}

	@JsonIgnore
	public Collection<String> getCurrencies() {
		return wallet.keySet();
	}
}
