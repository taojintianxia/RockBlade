package com.rockblade.persistence;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 18, 2013 3:53:59 PM
 * 
 */

public interface StockPersistence {

	public void saveStocks(Map<String, List<Stock>> stockList);
	
	public Map<String , Stock> getStockInSpecificDate(Calendar cal);
}
