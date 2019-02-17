package com.ismailkuyu.stocks.controller;


import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.exception.StockNotFoundException;
import com.ismailkuyu.stocks.repo.StockRepo;

@RestController
public class StockController {

	private final StockRepo repository;

	StockController(StockRepo repository) {
		this.repository = repository;
	}

	@GetMapping("/api/stocks")
	public List<Stock> all() {
		return repository.findAll();
	}
	
	@GetMapping("/api/stocks/{id}")
	public Stock one(@PathVariable Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new StockNotFoundException(id));
	}
	

	@PutMapping("/api/stocks/{id}")
	public Stock replaceStock(@RequestBody Stock newStock, @PathVariable Long id) {

		return repository.findById(id)
			.map(stock -> {
				stock.setName(newStock.getName());
				stock.setCurrentPrice(newStock.getCurrentPrice());
				stock.setLastUpdate(new Date());
				return repository.save(stock);
			})
			.orElseGet(() -> {
				newStock.setId(id);
				return repository.save(newStock);
			});
	}

	@PostMapping("/api/stocks")
	public Stock newStock(@RequestBody Stock newStock) {
		newStock.setLastUpdate(new Date());
		return repository.save(newStock);
	}


	@DeleteMapping("/api/stocks/{id}")
	public void deleteStock(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
