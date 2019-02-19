package com.ismailkuyu.stocks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.controller.StockController;

/**
*
* Integration test class for the {@link StockController}
* 
*
* @author Ismail Kuyu
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StocksApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
public class StockControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	/**
	 * Integration test for the method 'one' from {@link StockController}.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testOne() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/stocks/1", 
				HttpMethod.GET, entity, String.class);

		String expected = "{id:1,name:AAPL,currentPrice:170.0}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	
	/**
	 * Integration test for the method 'all' from {@link StockController}.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testAll() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/stocks/", 
				HttpMethod.GET, entity, String.class);

		String expected = "[{id:1,name:AAPL,currentPrice:170.0},{id:2,name:MSFT,currentPrice:108.22},{id:3,name:GOOGL,currentPrice:1119.63}]";

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	
	/**
	 * Integration test for the method 'newStock' from {@link StockController}.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testNewStock() throws JSONException {
		Stock stock = new Stock(5L, "ITEST", 5.0);
		
		HttpEntity<Stock> entity = new HttpEntity<Stock>(stock, headers);
		
		ResponseEntity<Stock> response = restTemplate.postForEntity("http://localhost:" + port + "/api/stocks/", entity, Stock.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(stock.getName(), response.getBody().getName());
		
		HttpEntity<Long> deleteEntity = new HttpEntity<Long>(stock.getId(), headers);
		
		ResponseEntity<String> deleteResponse = restTemplate.exchange("http://localhost:" + port + "/api/stocks/"+stock.getId(), HttpMethod.DELETE, deleteEntity, String.class);
		assertThat(deleteResponse.getStatusCode(), equalTo(HttpStatus.OK));
		
	}
	
	/**
	 * Integration test for the method 'replaceStock' from {@link StockController}.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testReplaceStock() throws JSONException {
		Stock stock = new Stock(6L, "GOOGL2", 5.0);
		
		HttpEntity<Stock> creationEntity = new HttpEntity<Stock>(stock, headers);
		
		ResponseEntity<Stock> creationResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/stocks/", creationEntity, Stock.class);
		assertThat(creationResponse.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(stock.getName(), creationResponse.getBody().getName());
		
		stock.setName("GOOGL3");
		
		HttpEntity<Stock> updateEntity = new HttpEntity<Stock>(stock, headers);
		
		ResponseEntity<Stock> updateResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/stocks/", updateEntity, Stock.class);
		assertThat(updateResponse.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(stock.getName(), updateResponse.getBody().getName());

		HttpEntity<Long> deleteEntity = new HttpEntity<Long>(stock.getId(), headers);
		
		ResponseEntity<String> deleteResponse = restTemplate.exchange("http://localhost:" + port + "/api/stocks/"+stock.getId(), HttpMethod.DELETE, deleteEntity, String.class);
		assertThat(deleteResponse.getStatusCode(), equalTo(HttpStatus.OK));

	}
	
	/**
	 * Integration test for the method 'deleteStock' from {@link StockController}.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testDeleteStock() throws JSONException {
		Stock stock = new Stock(4L, "GOOGL2", 5.0);
		
		HttpEntity<Stock> creationEntity = new HttpEntity<Stock>(stock, headers);
		
		ResponseEntity<Stock> creationResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/stocks/", creationEntity, Stock.class);
		assertThat(creationResponse.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(stock.getName(), creationResponse.getBody().getName());
		
		HttpEntity<Long> deleteEntity = new HttpEntity<Long>(stock.getId(), headers);
		
		ResponseEntity<String> deleteResponse = restTemplate.exchange("http://localhost:" + port + "/api/stocks/"+stock.getId(), HttpMethod.DELETE, deleteEntity, String.class);
		assertThat(deleteResponse.getStatusCode(), equalTo(HttpStatus.OK));
	}

	
}
