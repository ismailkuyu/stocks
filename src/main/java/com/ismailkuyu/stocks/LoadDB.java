package com.ismailkuyu.stocks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.repo.StockRepo;


@Configuration
class LoadDB {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDB.class);

	@Bean
	CommandLineRunner initDatabase(StockRepo repository) {
		
		return args -> {
			log.info("Preloading " + repository.save(new Stock("AAPL", 170.0)));
			log.info("Preloading " + repository.save(new Stock("MSFT", 108.22)));
			log.info("Preloading " + repository.save(new Stock("GOOGL", 1119.63)));
		};
	}
}
