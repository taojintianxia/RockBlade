package com.rockblade.invoker;

import static com.rockblade.util.StockUtil.AFTERNOON_START;
import static com.rockblade.util.StockUtil.MINUTE;
import static com.rockblade.util.StockUtil.TOP_NUM;
import static com.rockblade.util.StockUtil.isInMiddayNoneTradingTime;
import static com.rockblade.util.StockUtil.isInTradingTime;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import com.rockblade.cache.StockCache;
import com.rockblade.calculatecenter.impl.TopAbsoluteAskRatioStocks;
import com.rockblade.calculatecenter.impl.TopAbsoluteAskStocks;
import com.rockblade.calculatecenter.impl.TopAmountStocks;
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 3, 2013 11:36:02 AM
 * 
 */

public class CalculaterCenterInvoker extends TimerTask {

	@Override
	public void run() {
		while (true) {
			if (isInTradingTime()) {
				Map<String, List<Stock>> recentStocksMap = new HashMap<>();
				recentStocksMap = StockCache.getStocksInPreviousTime(StockCache.ALL_STOCKS_CACHE, MINUTE);
				if(!recentStocksMap.isEmpty()){
					TopAmountStocks topAmountStockCal = new TopAmountStocks();
					TopAbsoluteAskStocks topAbsoluteAskStocks = new TopAbsoluteAskStocks();
					TopAbsoluteAskRatioStocks topAbsoluteAskRatioStocks = new TopAbsoluteAskRatioStocks();
					System.out.println("===========================================================================");
					System.out.println("成交量前" + TOP_NUM + "的是 : ");
					printStocks(topAmountStockCal.getTopStocks(TOP_NUM, recentStocksMap), recentStocksMap);
					System.out.println("买一净值前" + TOP_NUM + "的是 : ");
					printStocks(topAbsoluteAskStocks.getTopStocks(TOP_NUM, recentStocksMap), recentStocksMap);
					System.out.println("买一净比前" + TOP_NUM + "的是 : ");
					printStocks(topAbsoluteAskRatioStocks.getTopStocks(TOP_NUM, recentStocksMap), recentStocksMap);
					System.out.println("===========================================================================");
					System.out.println("\n\n");
					recentStocksMap.clear();
				}
				try {
					Thread.sleep(TOP_NUM * MINUTE / 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (isInMiddayNoneTradingTime()) {
				Calendar currentTime = Calendar.getInstance();
				try {
					Thread.sleep(AFTERNOON_START.getTimeInMillis() - currentTime.getTimeInMillis());
				} catch (InterruptedException e) {
				}
			} else {
				System.out.println("finished , not trading time");
				break;
			}
		}
	}

	private void printStocks(Set<String> stockIdsList, Map<String, List<Stock>> recentStocksMap) {
		if (stockIdsList.size() > 0) {
			for (String str : stockIdsList) {
				List<Stock> stockList = recentStocksMap.get(str);
				if (!stockList.isEmpty()) {
					int size = stockList.size();
					System.out.println(stockList.get(size - 1));
				}
			}
		}
	}

}
