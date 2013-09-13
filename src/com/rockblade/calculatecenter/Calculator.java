package com.rockblade.calculatecenter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rockblade.invoker.ParserInvoker;
import com.rockblade.model.Stock;
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

	public static void main(String... args) {

	}

	public static List<Stock> getTopNStockAmount(int n) {
		new ParserInvoker();

		Map<String, Long> allStocks = new LinkedHashMap<>();
		StockUtil.sortMapByValue(allStocks);

		for (Map.Entry<String, Long> entry : allStocks.entrySet()) {
			System.out.println(entry.getKey() + "--->" + entry.getValue());
		}

		return null;
	}
}
