package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.rockblade.calculatecenter.TopNCalculator;
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 4:55:03 PM
 * 
 */

public abstract class AbstractTopNCalculator implements TopNCalculator {

	@Override
	public abstract List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap);

	protected List<String> sortAndGetTopNByMapValueInSequence(int n, Map<String, Double> targetMap, Comparator<Double> comparator) {
		return sortAndGetTopNByMapValue(n, targetMap, comparator, true);
	}

	protected List<String> sortAndGetTopNByMapValueInRevertedSequence(int n, Map<String, Double> targetMap, Comparator<Double> comparator) {
		return sortAndGetTopNByMapValue(n, targetMap, comparator, false);
	}

	private List<String> sortAndGetTopNByMapValue(int n, Map<String, Double> targetMap, Comparator<Double> comparator, final boolean isInSequence) {

		List<Map.Entry<String, Double>> stockEntryList = new ArrayList<>(n);
		List<String> resultList = new ArrayList<>(n);
		int stockSize = targetMap.size();
		int counter = 0;
		Double lastElement = Double.MIN_VALUE;
		boolean isSorted = false;
		if (n >= stockSize) {
			throw new IllegalArgumentException("N is more than stocks size !");
		}

		for (Map.Entry<String, Double> entry : targetMap.entrySet()) {
			if (counter < n) {
				stockEntryList.add(entry);
				counter++;
			} else {
				if (!isSorted) {
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
					isSorted = true;
					lastElement = stockEntryList.get(n - 1).getValue();
				}
				if (lastElement > entry.getValue()) {
					continue;
				} else {
					swapEntryList(stockEntryList, entry);
					lastElement = stockEntryList.get(stockEntryList.size() - 1).getValue();
				}
			}
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

}
