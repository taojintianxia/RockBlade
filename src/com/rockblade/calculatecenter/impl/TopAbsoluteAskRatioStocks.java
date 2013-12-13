package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 6, 2013 1:59:08 PM
 * 
 */

public class TopAbsoluteAskRatioStocks extends AbstractTopNCalculator {
	
	@Override
	public List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		List<Stock> topStocks = new ArrayList<>(n);
		Map<String, Double> absoluteAskRatioStockMap = new HashMap<>();
		List<String> topAbsoluteAskRatioStocksId = new ArrayList<>();
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

		topAbsoluteAskRatioStocksId = getTopNByMapValueInRevertedSequence(topNum, absoluteAskRatioStockMap);

		for (String stockId : topAbsoluteAskRatioStocksId) {
			List<Stock> stocksList = stocksMap.get(stockId);
			topStocks.add(stocksList.get(stocksList.size() - 1));
		}

		return topStocks;
	}

	@Override
	public List<Stock> getTopStocks(Map<String, List<Stock>> stocksMap) {
		return getTopStocks(N, stocksMap);
	}

}
