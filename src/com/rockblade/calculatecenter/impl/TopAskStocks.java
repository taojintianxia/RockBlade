package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 6, 2013 1:36:50 PM
 * 
 */

public class TopAskStocks extends AbstractTopNCalculator {

	@Override
	public List<String> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> stockAskMap = new HashMap<>();
		List<String> topAskStocksId = new ArrayList<>();
		Double totalAsk = 0.0;
		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stocks = entry.getValue();
			int stockSize = stocks.size();
			if (!stocks.isEmpty()) {
				totalAsk = 0.0;
				for (int i = 0; i < stockSize; i++) {
					if (stocks.get(i) != stocks.get(i - 1)) {
						totalAsk += stocks.get(i).getAsk1Price();
					}
				}
				stockAskMap.put(stockId, totalAsk);
			}
		}

		topAskStocksId = getTopNByMapValueInRevertedSequence(topNum, stockAskMap);

		return topAskStocksId;
	}

	@Override
	public List<String> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}

}
