package com.rockblade.calculatecenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rockblade.sevlet.Executor;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 9, 2013 12:59:48 PM
 * 
 */

/**
 * 
 * 
 * @author Kane.Sun
 * 
 */
public class Calculator {

	// public Map<String, Long> getTopMaximumAmountStock(int n) {
	// Map<String, Map<String, Long>> cacheMap = StockCache.getStockMapPool();
	// Map<Integer, Map<String, Long>> topNMap = new HashMap<>();
	//
	// }

	public static void main(String... args) {
		getTopNStockAmount(10);
	}

	public static Map<String, Long> getTopNStockAmount(int n) {
		Executor.init();

		Map<String, Long> allStocks = new LinkedHashMap<>();
		StockUtil.sortMapByValue(allStocks);

		for (Map.Entry<String, Long> entry : allStocks.entrySet()) {
			System.out.println(entry.getKey() + "--->" + entry.getValue());
		}

		return null;
	}

}
