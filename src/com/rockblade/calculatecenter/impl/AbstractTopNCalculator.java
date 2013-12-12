package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public abstract List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap);

	protected List<String> getTopNByMapValueInSequence(int n, Map<String, Double> targetMap) {
		return sortAndGetTopNByMapValue(n, targetMap, true);
	}

	protected List<String> getTopNByMapValueInRevertedSequence(int n, Map<String, Double> targetMap) {
		return sortAndGetTopNByMapValue(n, targetMap, false);
	}

	private List<String> sortAndGetTopNByMapValue(int topNum, Map<String, Double> targetMap, final boolean isInSequence) {

		List<Map.Entry<String, Double>> stockEntryList = new ArrayList<>(topNum);
		List<String> resultList = new ArrayList<>(topNum);
		int stockSize = targetMap.size();
		int counter = 0;
		Double lastElement = Double.MIN_VALUE;
		boolean isSorted = false;

		if (stockSize < 1) {
			return resultList;
		}

		for (Map.Entry<String, Double> entry : targetMap.entrySet()) {
			if (counter < topNum && counter < stockSize) {
				stockEntryList.add(entry);
				counter++;
			} else {
				if (!isSorted) {
					sortStockEntryList(stockEntryList, isInSequence);
					isSorted = true;
					lastElement = stockEntryList.get(topNum - 1).getValue();
				}

				if (lastElement > entry.getValue()) {
					continue;
				} else {
					swapEntryList(stockEntryList, entry);
					lastElement = stockEntryList.get(stockEntryList.size() - 1).getValue();
				}
			}
		}

		// what if N is less than map size
		if (stockSize <= topNum) {
			sortStockEntryList(stockEntryList, isInSequence);
		}

		for (Map.Entry<String, Double> entry : stockEntryList) {
			resultList.add(entry.getKey());
		}

		return resultList;
	}

	private void swapEntryList(List<Map.Entry<String, Double>> topNEntry, Map.Entry<String, Double> newEntry) {

		int size = topNEntry.size();
		int index = size - 1;
		for (int i = size - 2; i >= 0; i--) {
			if (topNEntry.get(i).getValue() < newEntry.getValue()) {
				index = i;
			} else {
				break;
			}
		}

		for (int i = size - 1; i > index; i--) {
			topNEntry.set(i, topNEntry.get(i - 1));
		}

		topNEntry.set(index, newEntry);
	}

	private void sortStockEntryList(List<Map.Entry<String, Double>> stockEntryList, final boolean isInSequence) {
		Collections.sort(stockEntryList, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
				if (a.getValue() > b.getValue())
					if (isInSequence) {
						return 1;
					} else {
						return -1;
					}
				else if (a.getValue() < b.getValue())
					if (isInSequence) {
						return -1;
					} else {
						return 1;
					}
				else
					return 0;
			}
		});
	}
}
