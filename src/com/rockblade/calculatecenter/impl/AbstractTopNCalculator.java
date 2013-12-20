package com.rockblade.calculatecenter.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rockblade.calculatecenter.TopCalculator;
import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 4:55:03 PM
 * 
 */

public abstract class AbstractTopNCalculator implements TopCalculator {

	protected final int topNum = StockUtil.TOP_NUM;

	@Override
	public abstract Set<String> getTopStocks(Map<String, List<Stock>> stocksMap);

	protected Set<String> getExpectedStockInSequence(Map<String, Double> targetMap) {
		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
		sortedMap.putAll(targetMap);
		StockUtil.sortMapByValueInSequence(sortedMap);
		return sortedMap.keySet();
	}

	protected Set<String> getExpetedStockInRevertedSequence(Map<String, Double> targetMap) {
		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
		sortedMap.putAll(targetMap);
		StockUtil.sortMapByValueInRevertedSequence(sortedMap);
		return sortedMap.keySet();
	}
}
