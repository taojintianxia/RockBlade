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
 * @version Dec 2, 2013 6:21:38 PM
 * 
 */

public class TOPNBidStocks extends AbstractTopNCalculator {

	@Override
	public List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		List<Stock> topStocks = new ArrayList<>(n);
		Map<String, Double> stockBidMap = new HashMap<>();
		List<String> topBidStocksId = new ArrayList<>();
		Double totalBid = 0.0;
		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stocks = entry.getValue();
			int stockSize = stocks.size();
			if (stockSize > 1) {
				totalBid = stocks.get(0).getBid1Price();
				for (int i = 1; i < stockSize; i++) {
					if (stocks.get(i) != stocks.get(i - 1)) {
						totalBid += stocks.get(i).getBid1Price();
					}
				}
			}
			stockBidMap.put(stockId, totalBid);
		}

		topBidStocksId = getTopNByMapValueInRevertedSequence(N, stockBidMap);

		for (String stockId : topBidStocksId) {
			List<Stock> stocksList = stocksMap.get(stockId);
			topStocks.add(stocksList.get(stocksList.size() - 1));
		}

		return topStocks;
	}

}
