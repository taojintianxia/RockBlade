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

public class TopAskStocks extends AbstractTopNCalculator{

	@Override
	public List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		List<Stock> topStocks = new ArrayList<>(n);
		Map<String, Double> stockAskMap = new HashMap<>();
		List<String> topAskStocksId = new ArrayList<>();
		Double totalAsk = 0.0;
		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stocks = entry.getValue();
			int stockSize = stocks.size();
			if (stockSize > 1) {
				totalAsk = stocks.get(0).getAsk1Price();
				for (int i = 1; i < stockSize; i++) {
					if (stocks.get(i) != stocks.get(i - 1)) {
						totalAsk += stocks.get(i).getAsk1Price();
					}
				}
			}
			stockAskMap.put(stockId, totalAsk);
		}

		topAskStocksId = getTopNByMapValueInRevertedSequence(topNum, stockAskMap);

		for (String stockId : topAskStocksId) {
			List<Stock> stocksList = stocksMap.get(stockId);
			topStocks.add(stocksList.get(stocksList.size() - 1));
		}

		return topStocks;
	}
	
	@Override
	public List<Stock> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N,stocksMap);
	}

}
