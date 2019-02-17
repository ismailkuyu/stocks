package com.ismailkuyu.stocks;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.controller.StockController;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StockControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockController stockController;

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
    
//    @Test
//    public void one() throws Exception {
//    	long id = 5L;
//    	
//    	Stock stock = new Stock(id, "Test Stock 2", new BigDecimal(2));
//    	given(stockController.one(id)).willReturn(stock);
//
//    	this.mockMvc.perform(get("/api/stocks/"+id))
//    			.andExpect(status().isOk())
//    			.andExpect(content().json("[{'id': "+id+";'name': 'Test Stock 2';'currentPrice': 2}]"));
//    }

	

}
