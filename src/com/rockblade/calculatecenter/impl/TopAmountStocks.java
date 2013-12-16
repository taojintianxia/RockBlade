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
 * @version Dec 3, 2013 11:27:41 AM
 * 
 */

public class TopAmountStocks extends AbstractTopNCalculator {

	@Override
	public List<String> getTopStocks(int topNum, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> stockAmountMap = new HashMap<>();
		List<String> topAmountStocksId = new ArrayList<>();

		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			List<Stock> stockList = entry.getValue();
			stockAmountMap.put(entry.getKey(), stockList.get(stockList.size() - 1).getAmount());
		}

		topAmountStocksId = getTopNByMapValueInRevertedSequence(topNum, stockAmountMap);

		return topAmountStocksId;
	}

	@Override
	public List<String> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}

}
