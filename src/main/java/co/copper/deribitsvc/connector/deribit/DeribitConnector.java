package co.copper.deribitsvc.connector.deribit;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.copper.deribitsvc.connector.ExchangeConnector;
import co.copper.deribitsvc.exception.ApiCallException;
import co.copper.deribitsvc.exception.AuthFailureException;
import co.copper.deribitsvc.model.CurrencyInfo;
import co.copper.deribitsvc.model.Deposit;
import co.copper.deribitsvc.model.Transfer;
import co.copper.deribitsvc.model.TransferToSubaccountReport;
import co.copper.deribitsvc.model.Wallet;
import co.copper.deribitsvc.model.WithdrawReport;
import co.copper.deribitsvc.model.Withdrawal;

public class DeribitConnector implements ExchangeConnector {
	public static final URI API_URI;

	static {
		try {
			API_URI = new URI("https://test.deribit.com/api/v2/");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private RestTemplate restTemplate;

	public DeribitConnector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Wallet getWallet(Map<String, String> authParams) {
		AuthInfo auth = authenticate(authParams);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("private/get_subaccounts").queryParam("with_portfolio", "true");
		URI uri = builder.build(Map.of());

		ResponseEntity<SubAccountsInfoResponse> resp = callEndpoint(uri,
				auth, SubAccountsInfoResponse.class);

		Wallet w = new Wallet();

		resp.getBody().getResult().forEach(
				si -> si.getPortfolio().forEach(
						(k, v) -> w.merge(k, createCurrencyInfo(v))));

		return w;
	}

	private CurrencyInfo createCurrencyInfo(AssetInfo v) {
		return new CurrencyInfo(v.getBalance(), v.getAvailableFunds());
	}

	private AuthInfo authenticate(Map<String, String> authParams) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("public/auth");

		for (Map.Entry<String, String> me : authParams.entrySet()) {
			builder.queryParam(me.getKey(), me.getValue());
		}

		try {
			AuthResponse resp = restTemplate.getForObject(builder.build(Map.of()), AuthResponse.class);
			return resp.getResult();
		} catch (Exception e) {
			throw new AuthFailureException("Authentication failed");
		}
	}

	private HttpHeaders createHeaders(AuthInfo auth) {
		return new HttpHeaders() {
			{
				String authHeader = auth.getTokenType() + " " + auth.getAcccessToken();
				set("Authorization", authHeader);
			}
		};
	}

	@Override
	public List<Deposit> getDeposits(String currency, int count, int offset, Map<String, String> authParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("private/get_deposits");

		builder.queryParam("currency", currency);
		builder.queryParam("count", count);
		builder.queryParam("offset", offset);

		AuthInfo auth = authenticate(authParams);
		ResponseEntity<DepositsResponse> resp = callEndpoint(builder.build(Map.of()),
				auth, DepositsResponse.class);

		return resp.getBody().getData();
	}

	@Override
	public List<Withdrawal> getWithdrawals(String currency, int count, int offset, Map<String, String> authParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("private/get_withdrawals");

		builder.queryParam("currency", currency);
		builder.queryParam("count", count);
		builder.queryParam("offset", offset);

		AuthInfo auth = authenticate(authParams);
		ResponseEntity<WithdrawalsResponse> resp = callEndpoint(builder.build(Map.of()),
				auth, WithdrawalsResponse.class);

		return resp.getBody().getData();
	}

	@Override
	public List<Transfer> getTransfers(String currency, int count, int offset, Map<String, String> authParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("private/get_transfers");

		builder.queryParam("currency", currency);
		builder.queryParam("count", count);
		builder.queryParam("offset", offset);

		AuthInfo auth = authenticate(authParams);
		ResponseEntity<TransfersResponse> resp = callEndpoint(builder.build(Map.of()),
				auth, TransfersResponse.class);

		return resp.getBody().getData();
	}

	@Override
	public WithdrawReport withdraw(String currency, String address, BigDecimal amount,
			Map<String, String> authParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("private/withdraw");

		builder.queryParam("currency", currency);
		builder.queryParam("address", address);
		builder.queryParam("amount", amount);

		AuthInfo auth = authenticate(authParams);
		ResponseEntity<WithdrawResponse> resp = callEndpoint(builder.build(Map.of()), auth, WithdrawResponse.class);
		return resp.getBody().getResult();

	}

	@Override
	public TransferToSubaccountReport transferToSubaccount(String currency, int account, BigDecimal value,
			Map<String, String> authParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(API_URI);
		builder.path("private/submit_transfer_to_subaccount");

		builder.queryParam("currency", currency);
		builder.queryParam("destination", account);
		builder.queryParam("amount", value);

		AuthInfo auth = authenticate(authParams);
		ResponseEntity<TransferToSubAccountResponse> resp = callEndpoint(builder.build(Map.of()),
				auth, TransferToSubAccountResponse.class);
		return resp.getBody().getResult();
	}
	
	private <T> ResponseEntity<T> callEndpoint(URI uri, AuthInfo auth, Class<T> respClass) {
		try {
			ResponseEntity<T> resp = restTemplate.exchange(uri, HttpMethod.GET,
					new HttpEntity<Void>(createHeaders(auth)), respClass);
			
			return resp;
		} catch (HttpClientErrorException e) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String s = e.getResponseBodyAsString();
				ErrorResponse err = mapper.readValue(s, ErrorResponse.class);
				throw new ApiCallException(err.getMessage());
			} catch (JsonProcessingException e1) {
				throw e;
			}
		}
	}
}
