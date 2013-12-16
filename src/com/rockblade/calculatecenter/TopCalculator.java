package com.rockblade.calculatecenter;

import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 3:22:20 PM
 * 
 */

public interface TopCalculator {
	
	public static int N = StockUtil.TOP_NUM;
	
	public abstract List<String> getTopStocks(int topN , Map<String , List<Stock>> stocksMap);
	
	public List<String> getTopStocks(Map<String , List<Stock>> stocksMap);

}
