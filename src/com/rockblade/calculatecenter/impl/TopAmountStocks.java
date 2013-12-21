package com.rockblade.calculatecenter.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rockblade.calculatecenter.rules.impl.RaisingLimitExceptRule;
//github.com/taojintianxia/RockBlade.git
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
	public Set<String> getTopStocks(int topN, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> stockAmountMap = new HashMap<>();
		Set<String> topAmountStocksId = new LinkedHashSet<>();

		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			List<Stock> stockList = entry.getValue();
			stockAmountMap.put(entry.getKey(), stockList.get(stockList.size() - 1).getAmount());
		}

		topAmountStocksId = getExpetedStockInRevertedSequence(stockAmountMap);
		topAmountStocksId = RaisingLimitExceptRule.getInstance().filter(topN, topAmountStocksId, stocksMap);
		
		return topAmountStocksId;
	}

	@Override
	public Set<String> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}

}
