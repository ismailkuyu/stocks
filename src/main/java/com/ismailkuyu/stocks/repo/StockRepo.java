package com.ismailkuyu.stocks.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ismailkuyu.stocks.bean.Stock;

@Repository("StockRepo")
public interface StockRepo extends JpaRepository<Stock, Long>{

}
