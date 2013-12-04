package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 3, 2013 11:27:41 AM
 * 
 */

public class TOPNAmountStocks extends AbstractTopNCalculator {

	@Override
	public List<Stock> getTopStocks(int topNum, Map<String, List<Stock>> stocksMap) {
		List<Stock> topStocks = new ArrayList<>(topNum);
		Map<String, Double> stockAmountMap = new HashMap<>();
		List<String> topAmountStocksId = new ArrayList<>();

		for (Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()) {
			List<Stock> stockList = entry.getValue();
			stockAmountMap.put(entry.getKey(), stockList.get(stockList.size() - 1).getAmount());
		}

		topAmountStocksId = getTopNByMapValueInRevertedSequence(topNum, stockAmountMap);

		for (String stockId : topAmountStocksId) {
			List<Stock> stocksList = stocksMap.get(stockId);
			topStocks.add(stocksList.get(stocksList.size() - 1));
		}

		return topStocks;
	}

	public static void main(String... args) {
		TOPNAmountStocks task = new TOPNAmountStocks();
		Map<String, List<Stock>> allStocksMap = new HashMap<>();
		Random random = new Random();
		System.out.println("===========================================================");
		for (int i = 0; i < 15; i++) {
			String id = random.nextInt(700000) + "";
			List<Stock> tempList = new ArrayList<>();
			double amount = 500;
			for (int j = 0; j < random.nextInt(3) + 1; j++) {
				Stock stock = StockUtil.getInitializedBlankStock();
				amount += random.nextInt(100);
				stock.setAmount(amount);
				stock.setStockId(id);
				stock.setStockName(id);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + random.nextInt(10000));
				stock.setTime(cal);
				tempList.add(stock);
			}
			allStocksMap.put(id, tempList);
		}
		
		printMap(allStocksMap);
		
		System.out.println("===========================================================");
		List<Stock > tmpList = task.getTopStocks(3,allStocksMap);
		System.out.println("top n are : ");
		for(Stock stock : tmpList){
			System.out.println(stock);
		}
 	}
	
	private static void printMap(Map<String , List<Stock>> stocksMap){
		for(Map.Entry<String, List<Stock>> entry : stocksMap.entrySet()){
			System.out.println(entry.getKey()+" : ");
			for(Stock stock : entry.getValue()){
				System.out.println(stock);
			}
			System.out.println("------------------------------------");
		}
	}
}
