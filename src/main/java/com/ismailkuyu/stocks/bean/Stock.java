package com.ismailkuyu.stocks.bean;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Stock {

	@Id 
	@GeneratedValue 
	private Long id;
	
	private String name;
	
	private double currentPrice;
	
	private Date lastUpdate;
	
	public Stock() {}
	
	public Stock(String name, double currentPrice){
		this.name = name;
		this.currentPrice = currentPrice;
		this.lastUpdate = new Date();
	}

	public Stock(Long id, String name, double currentPrice){
		this(name, currentPrice);
		this.id = id;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentPrice, id, lastUpdate, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Double.doubleToLongBits(currentPrice) == Double.doubleToLongBits(other.currentPrice)
				&& Objects.equals(id, other.id) && Objects.equals(lastUpdate, other.lastUpdate)
				&& Objects.equals(name, other.name);
	}	
	
	

}
