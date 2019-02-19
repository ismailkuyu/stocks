package com.ismailkuyu.stocks.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.exception.StockException;
import com.ismailkuyu.stocks.repo.StockRepo;

@Service("StockService")
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepo stockRepo;
	
	
	@Override
	public List<Stock> all() {
		return stockRepo.findAll();
	}

	@Override
	public Stock one(Long id) {
		return stockRepo.findById(id)
				.orElseThrow(() -> new StockException("Stock ["+id+"] couldn't be found"));
	}

	@Override
	public Stock replaceStock(Stock newStock, Long id) {

		return stockRepo.findById(id).map(stock -> {
			stock.setName(newStock.getName());
			stock.setCurrentPrice(newStock.getCurrentPrice());
			stock.setLastUpdate(new Date());
			return stockRepo.save(stock);
		}).orElseGet(() -> {
			newStock.setId(id);
			return stockRepo.save(newStock);
		});
	}

	@Override
	public Stock newStock(Stock stock) {
		stock.setLastUpdate(new Date());
		return stockRepo.save(stock);
	}

	@Override
	public void delete(Long id) {
		stockRepo.deleteById(id);
	}

}
