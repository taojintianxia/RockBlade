package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 28, 2013 3:21:17 PM
 * 
 */

public class TopPercentGrowthStocks extends AbstractTopNCalculator {

	@Override
	public List<String> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> stockPercentDifferenceMap = new LinkedHashMap<>();
		List<String> topStockIdsList = new ArrayList<>(n);
		for (Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stockList = entry.getValue();
			int stockSize = stockList.size();
			Double totalPercentDifference = -10.0;
			if (stockSize > 1) {
				for (int i = 1; i < stockSize; i++) {
					totalPercentDifference += stockList.get(i).getPercent() - stockList.get(i - 1).getPercent();
				}
				stockPercentDifferenceMap.put(stockId, totalPercentDifference);
			} else if (!stockList.isEmpty()) {
				stockPercentDifferenceMap.put(stockId, stockList.get(0).getPercent());
			}
		}

		topStockIdsList = getTopNByMapValueInRevertedSequence(topNum, stockPercentDifferenceMap);

		return topStockIdsList;
	}

	@Override
	public List<String> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}
}
