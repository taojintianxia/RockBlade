package com.rockblade.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

	public final static int SH_STOCK_CACHE_NUM = 1000;
	public final static int ZH_STOCK_CACHE_NUM = 1500;

	private static Map<String, Map<Date, Stock>> allStocksMapPool = new HashMap<>();
	private static Map<String, BasicStockInfo> basicStockInfoPool = new HashMap<>();
	private static Map<String, Stock> currentSHStockMap = new LinkedHashMap<>();
	private static List<String> SHStockIdList = new ArrayList<>(SH_STOCK_CACHE_NUM);
	private static List<String> SZStockIdList = new ArrayList<>(ZH_STOCK_CACHE_NUM);
	private static Map<String, Map<Date, Stock>> SHStockMap = new LinkedHashMap<>();
	private static Map<String, Map<Date, Stock>> SZStockMap = new LinkedHashMap<>();

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

	public static List<String> getSHStockIdList() {
		return SHStockIdList;
	}

	public static void setSHStockIdList(List<String> sHStockIdList) {
		SHStockIdList = sHStockIdList;
	}

	public static List<String> getSZStockIdList() {
		return SZStockIdList;
	}

	public static void setSZStockIdList(List<String> sZStockIdList) {
		SZStockIdList = sZStockIdList;
	}

	public static Map<String, Stock> getCurrentSHStockMap() {
		return currentSHStockMap;
	}

	public static void setCurrentSHStockMap(Map<String, Stock> currentSHStockMap) {
		StockCache.currentSHStockMap = currentSHStockMap;
	}

}
