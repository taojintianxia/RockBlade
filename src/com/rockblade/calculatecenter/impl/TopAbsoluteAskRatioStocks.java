package com.rockblade.calculatecenter.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rockblade.calculatecenter.rules.impl.RaisingLimitExceptRule;
import com.rockblade.util.StockUtil;
//github.com/taojintianxia/RockBlade.git
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 6, 2013 1:59:08 PM
 * 
 */

public class TopAbsoluteAskRatioStocks extends AbstractTopNCalculator {

	@Override
	public Set<String> getTopStocks(int topN, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> absoluteAskRatioStockMap = new HashMap<>();
		Set<String> topAbsoluteAskRatioStocksId = new LinkedHashSet<>();
		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stocks = entry.getValue();
			if (!stocks.isEmpty()) {
				int stockSize = stocks.size();
				Stock lastStock = stocks.get(stockSize - 1);
				// do not count on suspended stock
				if (lastStock.isSuspension()) {
					continue;
				}

				if (lastStock.getAmount() > 0) {
					double askAmount = lastStock.getAsk1Price() * lastStock.getAsk1Volume();
					double bidAmount = lastStock.getBid1Price() * lastStock.getBid1Volume();
					absoluteAskRatioStockMap.put(stockId, (askAmount - bidAmount) * StockUtil.ABSOLUTE_ASK_RATIO_FACTOR / lastStock.getAmount());
				}
			}
		}

		topAbsoluteAskRatioStocksId = getExpetedStockInRevertedSequence(absoluteAskRatioStockMap);
		topAbsoluteAskRatioStocksId = RaisingLimitExceptRule.getInstance().filter(topN, topAbsoluteAskRatioStocksId, stocksMap);

		return topAbsoluteAskRatioStocksId;
	}

	@Override
	public Set<String> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}

}
