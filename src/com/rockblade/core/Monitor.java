package com.rockblade.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class Monitor {

	// public Map<String, Long> getTopMaximumAmountStock(int n) {
	// Map<String, Map<String, Long>> cacheMap = StockCache.getStockMapPool();
	// Map<Integer, Map<String, Long>> topNMap = new HashMap<>();
	//
	// }

	public static void main(String... args) {
		Map<String, Long> testMap = new LinkedHashMap<>();
		testMap.put("123", 100L);
		testMap.put("456", 200L);
		testMap.put("d56", 230L);
		testMap.put("x56", 98L);
		testMap.put("a56", 150L);

		List<Map.Entry<String, Long>> testList = new ArrayList<Map.Entry<String, Long>>(testMap.entrySet());

		for (Map.Entry<String, Long> map : testList) {
			System.out.println(map.getValue());
		}

		testMap = StockUtil.sortMapByValue(testMap);

		System.out.println("-------------------");
		Iterator<String> it = testMap.keySet().iterator();
		while (it.hasNext()) {
			System.out.println(testMap.get(it.next()));
		}
	}
}
