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
 * @version Dec 6, 2013 1:36:50 PM
 * 
 */

public class TopAskStocks extends AbstractTopNCalculator {

	@Override
	public Set<String> getTopStocks(int topN, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> stockAskMap = new HashMap<>();
		Set<String> topAskStocksId = new LinkedHashSet<>();
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

		topAskStocksId = getExpetedStockInRevertedSequence(stockAskMap);
		topAskStocksId = RaisingLimitExceptRule.getInstance().filter(topN, topAskStocksId, stocksMap);

		return topAskStocksId;
	}

	@Override
	public Set<String> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}

}
