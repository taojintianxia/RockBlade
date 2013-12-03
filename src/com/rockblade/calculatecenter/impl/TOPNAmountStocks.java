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

public class TOPNAmountStocks extends AbstractTopNCalculator{

	@Override
	public List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		List<Stock> topStocks = new ArrayList<>(n);
		Map<String, Double> stockAmountMap = new HashMap<>();
		List<String> topAmountStocksId = new ArrayList<>();
		
		for(Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()){
			List<Stock> stockList = entry.getValue();
			stockAmountMap.put(entry.getKey(), stockList.get(stockList.size()-1).getAmount());
		}
		
		topAmountStocksId = getTopNByMapValueInRevertedSequence(N, stockAmountMap);

		for (String stockId : topAmountStocksId) {
			List<Stock> stocksList = stocksMap.get(stockId);
			topStocks.add(stocksList.get(stocksList.size() - 1));
		}
		
		return topStocks;
	}

}
