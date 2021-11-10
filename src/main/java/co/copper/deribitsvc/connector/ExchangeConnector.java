package co.copper.deribitsvc.connector;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import co.copper.deribitsvc.model.Deposit;
import co.copper.deribitsvc.model.Transfer;
import co.copper.deribitsvc.model.TransferToSubaccountReport;
import co.copper.deribitsvc.model.Wallet;
import co.copper.deribitsvc.model.WithdrawReport;
import co.copper.deribitsvc.model.Withdrawal;

public interface ExchangeConnector {
	
	Wallet getWallet( Map<String, String> authParams );
	
	List<Withdrawal> getWithdrawals(String currency, int count, int offset, Map<String, String> authParams);
	
	List<Deposit> getDeposits(String currency, int count, int offset, Map<String, String> authParams);
	
	List<Transfer> getTransfers(String currency, int count, int offset, Map<String, String> authParams);

	WithdrawReport withdraw(String currency, String address, BigDecimal amount, Map<String, String> parseAuthInfo);

	TransferToSubaccountReport transferToSubaccount(String currency, int account, BigDecimal value,
			Map<String, String> parseAuthInfo);

}
