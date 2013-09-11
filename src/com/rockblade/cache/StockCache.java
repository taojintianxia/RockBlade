package com.rockblade.cache;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.rockblade.model.BasicStockInfo;
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 9, 2013 1:00:24 PM
 * 
 */

public class StockCache {

	private static Map<String, Map<Date, Stock>> allStocksMapPool = new LinkedHashMap<>();
	private static Map<String, BasicStockInfo> basicStockInfoPool = new LinkedHashMap<>();
	private static Map<String, Map<Date, Stock>> SHStockMap = new LinkedHashMap<>(1000);
	private static Map<String, Map<Date, Stock>> SZStockMap = new LinkedHashMap<>(1500);

	public static Map<String, BasicStockInfo> getBasicStockInfoPool() {
		return basicStockInfoPool;
	}

	public static void setBasicStockInfoPool(Map<String, BasicStockInfo> basicStockInfoPool) {
		StockCache.basicStockInfoPool = basicStockInfoPool;
	}

	public static Map<String, Map<Date, Stock>> getAllStocksMapPool() {
		return allStocksMapPool;
	}

	public static void setAllStocksMapPool(Map<String, Map<Date, Stock>> allStocksMapPool) {
		StockCache.allStocksMapPool = allStocksMapPool;
	}

	public static Map<String, Map<Date, Stock>> getSHStockMap() {
		return SHStockMap;
	}

	public static void setSHStockMap(Map<String, Map<Date, Stock>> sHStockMap) {
		SHStockMap = sHStockMap;
	}

	public static Map<String, Map<Date, Stock>> getSZStockMap() {
		return SZStockMap;
	}

	public static void setSZStockMap(Map<String, Map<Date, Stock>> sZStockMap) {
		SZStockMap = sZStockMap;
	}

}
