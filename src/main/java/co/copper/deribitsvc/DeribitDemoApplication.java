package co.copper.deribitsvc;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import co.copper.deribitsvc.connector.ExchangeConnector;
import co.copper.deribitsvc.connector.deribit.DeribitConnector;
import co.copper.deribitsvc.db.DbPersistence;
import co.copper.deribitsvc.db.PGDbPersistence;

@SpringBootApplication
public class DeribitDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeribitDemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public ExchangeConnector exchangeConnector(RestTemplate restTemplate) {
		return new DeribitConnector(restTemplate);

	}
	
	@Bean
	public DataSource dataSource( 
			@Value("${db}") String dbURL, 
			@Value("${dbuser}") String dbUser, 
			@Value("${dbpass}") String dbPass) throws PropertyVetoException
	{
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( dbURL );
		cpds.setUser(dbUser);                                  
		cpds.setPassword(dbPass);
		
		return cpds;
	}

//	@Bean
//	public DbPersistence dbPersistence( DataSource ds) {
//		return new PGDbPersistence(ds);
//	}
}
