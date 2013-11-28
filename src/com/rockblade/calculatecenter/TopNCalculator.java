package com.rockblade.calculatecenter;

import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 3:22:20 PM
 * 
 */

public interface TopNCalculator {
	
	public abstract List<Stock> getTopStocks(int n , Map<String , List<Stock>> stocksMap);

}
