package com.rockblade.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	// since every fixed time , there will be some stock data stores to DB.this
	// map stores the index of every stock.
	public static Map<String, Integer[]> persistenceIndexer = new HashMap<>();

	public static final Map<String, List<Stock>> ALL_STOCKS_CACHE = new ConcurrentHashMap<>();

	public static final Map<String, Boolean> ALL_STOCK_SAVED_MARKER = new ConcurrentHashMap<>();

	public static final List<String> ALL_STOCK_ID = new ArrayList<>();

	public static void cleanCache() {
		persistenceIndexer.clear();
		ALL_STOCKS_CACHE.clear();
	}

}
