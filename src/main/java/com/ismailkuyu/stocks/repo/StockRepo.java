package com.ismailkuyu.stocks.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ismailkuyu.stocks.bean.Stock;

public interface StockRepo extends JpaRepository<Stock, Long>{

}
