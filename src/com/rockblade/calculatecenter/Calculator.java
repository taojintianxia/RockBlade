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
		getTopNAmountInSHStock(10);
	}

	public static List<Stock> getTopNAmountInSHStock(int n) {
		ParserInvoker parserInvoker = new ParserInvoker();
		parserInvoker.updateStocks();
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

	/**
	 * get top n amount difference stock
	 * 
	 * @param n
	 * @return
	 */
	public static void calculateTopNAmountDifferenceInSHStock(int n) {
		Map<String, Map<Date, Stock>> allSHStockMap = StockCache.getSHStockMap();
		Map<String, Double> allSHAmountDifStockMap = new LinkedHashMap<>(allSHStockMap.size());
		Map<String, Double> shTopNAmountDifStockMap = new LinkedHashMap<>(n);
		double amountDif = 0;
		int counter = 0;
		for (Map.Entry<String, Map<Date, Stock>> entry : allSHStockMap.entrySet()) {
			amountDif = getMaximumAmountDiffer(entry.getValue());
			allSHAmountDifStockMap.put(entry.getKey(), amountDif);
		}

		StockUtil.sortMapByValueInDesc(allSHAmountDifStockMap);
		for (Map.Entry<String, Double> entry : allSHAmountDifStockMap.entrySet()) {
			if (counter == n) {
				break;
			}
			shTopNAmountDifStockMap.put(entry.getKey(), entry.getValue());
			counter++;
		}

		StockCache.setSHStockTopAmountDifferenceMap(shTopNAmountDifStockMap);
	}

	/**
	 * get the maximum amount difference for a specific stock
	 * 
	 * @param stockHistoryMap
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Double getMaximumAmountDiffer(Map<Date, Stock> stockHistoryMap) {
		double elementValue = 0;
		int counter = 0;

		int start = 0;
		int end = 0;
		int size = stockHistoryMap.size();
		double tmpSum = 0;
		double maxDiffer = 0;
		List<Double> differList = new ArrayList<>(size);

		for (Map.Entry<Date, Stock> entry : stockHistoryMap.entrySet()) {
			if (counter++ == 0) {
				elementValue = entry.getValue().getAmount();
				continue;
			}

			differList.add(elementValue - entry.getValue().getAmount());
			elementValue = entry.getValue().getAmount();
		}

		// get maximum sub array , this is the core algorithm of this method
		for (int i = 0; i < size - 1; i++) {
			tmpSum += differList.get(i);
			if (tmpSum > 0) {
				tmpSum = 0;
			} else if (tmpSum == differList.get(i)) {
				start = i;
			}

			if (maxDiffer > tmpSum) {
				maxDiffer = tmpSum;
				end = i;
			}
		}

		return maxDiffer;
	}
}
