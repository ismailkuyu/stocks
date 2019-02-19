package com.ismailkuyu.stocks.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.exception.StockException;
import com.ismailkuyu.stocks.repo.StockRepo;

/**
 * 
 * Service class for wrapping the repository class {@link StockRepo}
 * 
 * @author Ismail Kuyu
 *
 */
@Service("StockService")
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepo stockRepo;
	
	/**
	 * Service wrapper for method 'findAll' in class {@link StockRepo}.
	 * 
	 * @return List<Stock> list of stocks in database
	 */
	@Override
	public List<Stock> all() {
		return stockRepo.findAll();
	}

	/**
	 * Service wrapper for method 'findById' in class {@link StockRepo}.
	 * 
	 * @param id: id of the stock to get
	 * @return Stock: selected stock
	 */
	@Override
	public Stock one(Long id) {
		return stockRepo.findById(id)
				.orElseThrow(() -> new StockException("Stock ["+id+"] couldn't be found"));
	}

	/**
	 * Service wrapper for method 'save' in class {@link StockRepo}.
	 * 
	 * @param newStock: new stock 
	 * @param id: id of the stock that will be replaced
	 * @return Stock: new stock
	 */
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

	/**
	 * Service wrapper for method 'save' in class {@link StockRepo}.
	 * 
	 * @param stock: new stock 
	 * @return Stock: new stock
	 */
	@Override
	public Stock newStock(Stock stock) {
		stock.setLastUpdate(new Date());
		return stockRepo.save(stock);
	}

	/**
	 * Service wrapper for method 'deleteById' in class {@link StockRepo}.
	 * 
	 * @param id: id of the stock to be deleted
	 */
	@Override
	public void delete(Long id) {
		stockRepo.deleteById(id);
	}

}
