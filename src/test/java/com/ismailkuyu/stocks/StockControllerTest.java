package com.ismailkuyu.stocks;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

@RunWith(SpringRunner.class)
@WebMvcTest
public class StockControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockController stockController;
    
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

    @Test
    public void all() throws Exception {
        Stock stock1 = new Stock("Test Stock 1", 1L);

        List<Stock> stocks = Arrays.asList(stock1);
        given(stockController.all()).willReturn(stocks);

        this.mockMvc.perform(get("/api/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name", is(stock1.getName())))
                .andExpect(jsonPath("[0].currentPrice", is(stock1.getCurrentPrice())));
    }
    
    @Test
    public void one() throws Exception {
    	Long id = 5L;
    	
    	Stock stock = new Stock(id, "Test Stock 2", 2L);
    	given(stockController.one(id)).willReturn(stock);

    	this.mockMvc.perform(get("/api/stocks/"+id))
    			.andExpect(status().isOk())
                .andExpect(jsonPath("id", is(stock.getId())))
                .andExpect(jsonPath("name", is(stock.getName())))
                .andExpect(jsonPath("currentPrice", is(stock.getCurrentPrice())));
//    			.andExpect(content().json("[{'id': "+id+";'name': 'Test Stock 2';'currentPrice': 2}]"));
    }
    
    
//    @Test
//    public void replaceStock() throws Exception {
//    	
//    	Long id = 3L;
//    	
//    	Stock stock = new Stock(id, "Test Stock 3", 3L);
//    	given(stockController.replaceStock(newStock, id))
//    	
//    }

    
    

	

}
