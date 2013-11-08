package com.rockblade.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 4, 2013 1:47:03 PM
 * 
 */

public class AllStockCache {

	public static Map<String, Map<Date, Stock>> ALLSTOCKCACHE = new HashMap<>(2000);
	public static Map<String, Map<Date, Stock>> SH_STOCK_CACHE = new HashMap<>(883);
	public static Map<String, Map<Date, Stock>> SZ_STOCK_CACHE = new HashMap<>(2000);

}
