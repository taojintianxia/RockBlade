package com.rockblade.parsecenter;

import java.io.IOException;
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
	
	public List<Stock> getStocksByIds(List<String> stockIdList) throws InterruptedException, IOException;

	public Stock getStockById(String stockId);
	
}
