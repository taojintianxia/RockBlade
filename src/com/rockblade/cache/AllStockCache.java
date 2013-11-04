package com.rockblade.cache;

import java.sql.Time;
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

	public static Map<String, Map<Time, Stock>> ALLSTOCKCACHE = new HashMap<>(2000);

}
