package co.copper.deribitsvc.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.copper.deribitsvc.connector.ExchangeConnector;
import co.copper.deribitsvc.db.DbPersistence;
import co.copper.deribitsvc.model.Deposit;
import co.copper.deribitsvc.model.Transfer;
import co.copper.deribitsvc.model.TransferToSubaccountReport;
import co.copper.deribitsvc.model.Wallet;
import co.copper.deribitsvc.model.WithdrawReport;
import co.copper.deribitsvc.model.Withdrawal;

@RestController
public class WalletController {
	private ExchangeConnector connector;
	private DbPersistence dbPersistence;

	public WalletController(ExchangeConnector conn, DbPersistence db) {
		this.connector = conn;
		this.dbPersistence = db;
	}

	@RequestMapping("/wallet")
	Wallet getWallet(@RequestHeader(name = "X-Auth-Info") String authInfo) {

		Wallet w = connector.getWallet(parseAuthInfo(authInfo));

		CompletableFuture.supplyAsync(() -> storeWallet(w));

		return w;
	}

	@RequestMapping("/withdraw")
	WithdrawReport withdraw(
			@RequestHeader(name = "X-Auth-Info") String authInfo,
			@RequestParam String currency,
			@RequestParam String address,
			@RequestParam BigDecimal amount) {
		WithdrawReport wr = connector.withdraw(currency, address, amount, parseAuthInfo(authInfo));

		return wr;
	}

	@RequestMapping("/deposits")
	List<Deposit> getDeposits(
			@RequestHeader(name = "X-Auth-Info") String authInfo,
			@RequestParam String currency,
			@RequestParam(defaultValue = "1000") int count,
			@RequestParam(defaultValue = "0") int offset) {
		return connector.getDeposits(currency, count, offset, parseAuthInfo(authInfo));
	}

	@RequestMapping("/withdrawals")
	List<Withdrawal> getWithdrawals(
			@RequestHeader(name = "X-Auth-Info") String authInfo,
			@RequestParam String currency,
			@RequestParam(defaultValue = "1000") int count,
			@RequestParam(defaultValue = "0") int offset) {
		return connector.getWithdrawals(currency, count, offset, parseAuthInfo(authInfo));
	}

	@RequestMapping("/transfers")
	List<Transfer> getTransfers(
			@RequestHeader(name = "X-Auth-Info") String authInfo,
			@RequestParam String currency,
			@RequestParam(defaultValue = "1000") int count,
			@RequestParam(defaultValue = "0") int offset) {
		return connector.getTransfers(currency, count, offset, parseAuthInfo(authInfo));
	}
	
	@RequestMapping("/transfer_to_subaccount")
	TransferToSubaccountReport transferToSubaccount(
			@RequestHeader(name = "X-Auth-Info") String authInfo,
			@RequestParam String currency,
			@RequestParam() int account,
			@RequestParam() BigDecimal amount) {
		return connector.transferToSubaccount(currency, account, amount, parseAuthInfo(authInfo));
	}

	private Map<String, String> parseAuthInfo(String aInfo) {
		Map<String, String> params = new HashMap<>();
		MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString("http://x/x?" + aInfo).build()
				.getQueryParams();

		for (MultiValueMap.Entry<String, List<String>> me : parameters.entrySet()) {
			params.put(me.getKey(), me.getValue().get(0));
		}
		return params;
	}

	private boolean storeWallet(Wallet w) {
		dbPersistence.storeWallet(w);
		return true;
	}
}
