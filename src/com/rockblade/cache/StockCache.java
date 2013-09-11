package com.rockblade.cache;

import java.util.HashMap;
import java.util.Map;

import com.rockblade.model.BasicStockInfo;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 9, 2013 1:00:24 PM
 * 
 */

public class StockCache {

	private static Map<String, Map<String, Long>> allStocksMapPool = new HashMap<>();
	private static Map<String, BasicStockInfo> basicStockInfoPool = new HashMap<>();
	private static Map<String, String> SHStockMap = new HashMap<>(1000);
	private static Map<String, String> ZHStockMap = new HashMap<>(1500);

	public static Map<String, BasicStockInfo> getBasicStockInfoPool() {
		return basicStockInfoPool;
	}

	public static void setBasicStockInfoPool(Map<String, BasicStockInfo> basicStockInfoPool) {
		StockCache.basicStockInfoPool = basicStockInfoPool;
	}

	public static Map<String, Map<String, Long>> getStockMapPool() {
		return allStocksMapPool;
	}

	public static void setStockMapPool(Map<String, Map<String, Long>> allStocksMapPool) {
		StockCache.allStocksMapPool = allStocksMapPool;
	}

	public static Map<String, String> getSHStockMap() {
		return SHStockMap;
	}

	public static void setSHStockMap(Map<String, String> sHStockMap) {
		SHStockMap = sHStockMap;
	}

	public static Map<String, String> getZHStockMap() {
		return ZHStockMap;
	}

	public static void setZHStockMap(Map<String, String> zHStockMap) {
		ZHStockMap = zHStockMap;
	}

}
