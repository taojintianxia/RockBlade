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
		Double totalAbsoluteAsk = 0.0;
		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stocks = entry.getValue();
			int stockSize = stocks.size();
			if (stockSize > 1) {
				totalAbsoluteAsk = stocks.get(0).getAsk1Price();
				for (int i = 1; i < stockSize; i++) {
					if (stocks.get(i) != stocks.get(i - 1)) {
						totalAbsoluteAsk += stocks.get(i).getAsk1Price() - stocks.get(i).getBid1Price();
					}
				}
				absoluteAskRatioStockMap.put(stockId, totalAbsoluteAsk * StockUtil.ABSOLUTE_ASK_RATIO_FACTOR / stocks.get(stocks.size() - 1).getAmount());
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
