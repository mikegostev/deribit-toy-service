package co.copper.deribitsvc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import co.copper.deribitsvc.model.CurrencyInfo;
import co.copper.deribitsvc.model.Wallet;

@Component
public class PGDbPersistence implements DbPersistence {
	private DataSource dataSource;
	private Map<String, CurrencyInfo> lastStoredAmounts = new HashMap<>();

	public PGDbPersistence(DataSource ds) {
		this.dataSource = ds;

		initDb();
	}

	private void initDb() throws DbException {
		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS wallet("
					+ "tstamp bigint,"
					+ "currency varchar(255) PRIMARY KEY,"
					+ "total numeric,"
					+ "available numeric)");

			stmt.executeUpdate("CREATE INDEX IF NOT EXISTS wallet_log_ts_index on wallet_log (tstamp)");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException("DB operation error. See logs");
		}
	}

	@Override
	public void storeWallet(Wallet w) throws DbException {
		Set<String> toUpdateSet = checkNeedUpdate(w);

		if (toUpdateSet.size() == 0) {
			return;
		}

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO wallet"
								+ " (currency, tstamp, total,available)"
								+ " VALUES (?,?,?,?)"
								+ " ON CONFLICT (currency) DO UPDATE SET"
								+ " tstamp=?, total=?, available=?");) {
			long tstamp = System.currentTimeMillis();

			for (String curr : toUpdateSet) {
				CurrencyInfo ci = w.getCurrencyInfo(curr);
				pstmt.setString(1, curr);
				pstmt.setLong(2, tstamp);
				pstmt.setBigDecimal(3, ci.balance());
				pstmt.setBigDecimal(4, ci.available());
				pstmt.setLong(5, tstamp);
				pstmt.setBigDecimal(6, ci.balance());
				pstmt.setBigDecimal(7, ci.available());
				pstmt.executeUpdate();
				
				lastStoredAmounts.put(curr, ci);
			}
		} catch (Exception e) {
			throw new DbException("DB operation error. See logs");
		}
	}

	private Set<String> checkNeedUpdate(Wallet w) {
		return w.getWalletAsMap().entrySet()
				.stream()
				.filter(e -> !e.getValue().equals(lastStoredAmounts.get(e.getKey())))
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}
}
