package com.rockblade.parsecenter;

import java.util.List;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 5:23:08 PM
 * 
 */

public interface Parser {
	
	public List<Stock> getStocksByIdList(List<String> stockIdList);

	public Stock getStockById(String stockId);
}
