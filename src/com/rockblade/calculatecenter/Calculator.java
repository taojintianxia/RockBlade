package com.rockblade.calculatecenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rockblade.cache.StockCache;
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

public class Calculator {

	public static void main(String... args) {

	}

	public static List<Stock> getTopNAmountInSHStock(int n) {
		ParserInvoker parserInvoker = new ParserInvoker();
		Map<String, Stock> currentSHStockMap = StockCache.getCurrentSHStockMap();
		Map<String, Double> currentSHStockAmountMap = new LinkedHashMap<>(currentSHStockMap.size());
		List<Stock> topNSHStockAmountList = new ArrayList<>(n);
		for (Map.Entry<String, Stock> entry : currentSHStockMap.entrySet()) {
			currentSHStockAmountMap.put(entry.getKey(), entry.getValue().getAmount());
		}

		StockUtil.sortMapByValue(currentSHStockAmountMap);

		Iterator<String> it = currentSHStockAmountMap.keySet().iterator();
		for (int i = 0; i < n; i++) {
			topNSHStockAmountList.add(currentSHStockMap.get(it.next()));
		}

		return topNSHStockAmountList;
	}

	public static List<Stock> getTopNMoneyInjectionInSHStock(int n) {
		List<Stock> topNMoneyInjectionStockList = new ArrayList<>(n);
		Map<String, Map<Date, Stock>> allSHStockMap = StockCache.getSHStockMap();
		Map<String, Double> allSHStockAmountDiffMap = new LinkedHashMap<>(allSHStockMap.size());

		return topNMoneyInjectionStockList;
	}

	private Double getAmountDiffer(Map<Date, Stock> stockHistoryMap) {
		int start = 0;
		int end = 0;
		int size = stockHistoryMap.size();
		double tmpSum = 0;
		double maxmiumDiffer = 0;
		List<Double> differList = new ArrayList<>(size);

		return maxmiumDiffer;
	}
}
