package com.rockblade.cache;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author Kane.Sun
 * @version Sep 9, 2013 1:00:24 PM
 * 
 */

public class StockCache {
	
	public static Map<String, Map<String, Long>> stockMapPool = new HashMap<>();

	public static Map<String, Map<String, Long>> getStockMapPool() {
		return stockMapPool;
	}

	public static void setStockMapPool(Map<String, Map<String, Long>> stockMapPool) {
		StockCache.stockMapPool = stockMapPool;
	}

}
