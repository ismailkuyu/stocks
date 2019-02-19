package com.ismailkuyu.stocks;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.controller.StockController;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;


/**
 *
 * Unit test class for the {@link StockController}
 * 
 *
 * @author Ismail Kuyu
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureRestDocs
public class StockControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockController stockController;

	/**
	 * initializer for the configuration
	 */
	@Before
	public void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
//        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

		Configuration.setDefaults(new Configuration.Defaults() {

			private final JsonProvider jsonProvider = new JacksonJsonProvider(objectMapper);
			private final MappingProvider mappingProvider = new JacksonMappingProvider(objectMapper);

			@Override
			public JsonProvider jsonProvider() {
				return jsonProvider;
			}

			@Override
			public MappingProvider mappingProvider() {
				return mappingProvider;
			}

			@Override
			public Set<Option> options() {
				return EnumSet.noneOf(Option.class);
			}
		});

	}

	/**
	 * Unit test for the method 'all' from {@link StockController}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifyAll() throws Exception {
		Stock stock1 = new Stock(1L, "Test Stock 1", 1.0);
		Stock stock2= new Stock(2L, "Test Stock 2", 2.0);

		List<Stock> stocks = Arrays.asList(stock1, stock2);
		given(stockController.all()).willReturn(stocks);

		this.mockMvc.perform(get("/api/stocks"))
				.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1L)))
				.andExpect(jsonPath("$[0].name", is("Test Stock 1")))
				.andExpect(jsonPath("$[0].currentPrice", is(1.0)))
				.andExpect(jsonPath("$[1].id", is(2L)))
				.andExpect(jsonPath("$[1].name", is("Test Stock 2")))
				.andExpect(jsonPath("$[1].currentPrice", is(2.0)));
		
	    verify(stockController, times(1)).all();
	    verifyNoMoreInteractions(stockController);

	}

	/**
	 * Unit test for the method 'one' from {@link StockController}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifyOne() throws Exception {
		
		Stock stock = new Stock(1L, "Test Stock 1", 1.0);
		given(stockController.one(1L)).willReturn(stock);

		this.mockMvc.perform(get("/api/stocks/{id}", 1))
				.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.id", is(1L)))
	            .andExpect(jsonPath("$.name", is("Test Stock 1")))
				.andExpect(jsonPath("$.currentPrice", is(1.0)));
		
	    verify(stockController, times(1)).one(1L);
	    verifyNoMoreInteractions(stockController);
	}

	/**
	 * Unit test for the method 'replaceStock' from {@link StockController}.

	 * @throws Exception
	 */
	@Test
	public void verifyReplace() throws Exception {

		Stock stock = new Stock(1L, "Test Stock 1", 1.0);

		given(stockController.replaceStock(stock, 1L)).willReturn(stock);
		
		String json = mapToJson(stock);

		this.mockMvc.perform(put("/api/stocks/{id}", stock.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.characterEncoding("utf-8"))
		        .andExpect(jsonPath("$.id", is(1L)))
		        .andExpect(jsonPath("$.name", is("Test Stock 1")))
				.andExpect(jsonPath("$.currentPrice", is(1.0)))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());
		
		verify(stockController, times(1)).replaceStock(stock, stock.getId());
	    verifyNoMoreInteractions(stockController);

		
	}
	
	/**
	 * Unit test for the method 'newStock' from {@link StockController}.

	 * @throws Exception
	 */
	@Test
	public void verifyNew() throws Exception {

		Stock stock = new Stock(4L, "Test Stock 4", 4.0);
		String json = mapToJson(stock);
		
		given(stockController.newStock(stock)).willReturn(stock);
		

		this.mockMvc.perform(post("/api/stocks/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.id", is(4L)))
	            .andExpect(jsonPath("$.name", is("Test Stock 4")))
				.andExpect(jsonPath("$.currentPrice", is(4.0)));
		
		verify(stockController, times(1)).newStock(stock);
	    verifyNoMoreInteractions(stockController);

	}
	
	/**
	 * Unit test for the method 'deleteStock' from {@link StockController}.

	 * @throws Exception
	 */
	@Test
	public void verifyDelete() throws Exception {

		Stock stock = new Stock(4L, "Test Stock 4", 4.0);
		given(stockController.deleteStock(stock.getId())).willReturn("Stock ["+stock.getId()+"] is deleted.");

		this.mockMvc.perform(delete("/api/stocks/{id}", 4L)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());
		
		verify(stockController, times(1)).deleteStock(stock.getId());
	    verifyNoMoreInteractions(stockController);

	}
	
	
	

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

}
