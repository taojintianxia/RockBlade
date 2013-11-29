package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 28, 2013 3:21:17 PM
 * 
 */

public class TopNPercentGrowthStocks extends AbstractTopNCalculator {

	@Override
	public List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		Map<String, Double> stockPercentDiffMap = new LinkedHashMap<>();
		for (Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			String stockId = entry.getKey();
			List<Stock> stockList = entry.getValue();
			// stock is suspension
			if (stockList.size() < 2) {
				stockPercentDiffMap.put(stockId, 0.0);
			} else {
				double diff = stockList.get(stockList.size() - 1).getPercent() - stockList.get(0).getPercent();
				stockPercentDiffMap.put(stockId, diff);
			}
		}

		return getTopNStocks(n, stockPercentDiffMap, stocksMap);
	}

	protected List<Stock> getTopNStocks(int n, Map<String, Double> stockDiffMap, Map<String, List<Stock>> stocksMap) {
		List<Map.Entry<String, Double>> stockEntryList = new ArrayList<>(n);
		List<Stock> resultList = new ArrayList<>(n);
		int stockSize = stockDiffMap.size();
		int counter = 0;
		Double lastTopNStockPercent = 0.0;
		boolean isSorted = false;
		if (n >= stockSize) {
			throw new IllegalArgumentException("N is more than stocks size !");
		}

		for (Map.Entry<String, Double> entry : stockDiffMap.entrySet()) {
			if (counter < n) {
				stockEntryList.add(entry);
				counter++;
			} else {
				if (!isSorted) {
					Collections.sort(stockEntryList, new Comparator<Map.Entry<String, Double>>() {
						@Override
						public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
							if (a.getValue() > b.getValue())
								return -1;
							else if (a.getValue() < b.getValue())
								return 1;
							else
								return 0;
						}
					});
					isSorted = true;
					lastTopNStockPercent = stockEntryList.get(n - 1).getValue();
				}
				if (lastTopNStockPercent > entry.getValue()) {
					continue;
				} else {
					swapEntryList(stockEntryList, entry);
					lastTopNStockPercent = stockEntryList.get(stockEntryList.size()-1).getValue();
				}
			}
		}

		for (Map.Entry<String, Double> entry : stockEntryList) {
			List<Stock> tmpStocksList = stocksMap.get(entry.getKey());
			resultList.add(tmpStocksList.get(tmpStocksList.size() - 1));
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

	public static void main(String... args) {
		TopNPercentGrowthStocks calculator = new TopNPercentGrowthStocks();
		Map<String, List<Stock>> testMap = new HashMap<>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			String id = random.nextInt(700000) + "";
			List<Stock> tempList = new ArrayList<>();
			for (int j = 0; j < random.nextInt(3) + 2; j++) {
				Stock stock = StockUtil.getInitializedBlankStock();
				stock.setPercent(random.nextInt(10));
				if (random.nextBoolean()) {
					stock.setPercent(stock.getPercent() - 10);
				}
				stock.setStockId(id);
				tempList.add(stock);
			}
			testMap.put(id, tempList);
		}

		System.out.println("the test stock are as followings: ");

		for (Map.Entry<String, List<Stock>> entry : testMap.entrySet()) {
			List<Stock> stocksList = entry.getValue();
			System.out.print(" the stock " + entry.getKey() + " values are : ");
			for (Stock stock : stocksList) {
				System.out.print(stock.getPercent() + " , ");
			}
			System.out.print(" the differ is : " + (stocksList.get(stocksList.size() - 1).getPercent() - stocksList.get(0).getPercent()));
			System.out.println();
		}

		List<Stock> targetStockList = new ArrayList<>();
		targetStockList = calculator.getTopStocks(3, testMap);
		System.out.println("the top 3 are : ");
		for (Stock stock : targetStockList) {
			System.out.print(stock.getStockId() + " , ");
		}

	}
}
