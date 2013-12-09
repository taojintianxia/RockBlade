package com.rockblade.invoker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.rockblade.cache.StockCache;
import com.rockblade.calculatecenter.impl.TopAbsoluteAskRatioStocks;
import com.rockblade.calculatecenter.impl.TopAbsoluteAskStocks;
import com.rockblade.calculatecenter.impl.TopAmountStocks;
import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

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
			if (StockUtil.isInTradingTime()) {

				Map<String, List<Stock>> recentStocksMap = new HashMap<>();
				recentStocksMap = StockCache.getStocksInPreviousTime(StockUtil.TOP_NUM * StockUtil.MINUTE);
				TopAmountStocks topAmountStockCal = new TopAmountStocks();
				TopAbsoluteAskStocks topAbsoluteAskStocks = new TopAbsoluteAskStocks();
				TopAbsoluteAskRatioStocks topAbsoluteAskRatioStocks = new TopAbsoluteAskRatioStocks();
				System.out.println("Top Amount stocks are as following : ");
				printStocks(topAmountStockCal.getTopStocks(5, recentStocksMap));
				System.out.println("Top Absolute Ask stocks are as following : ");
				printStocks(topAbsoluteAskStocks.getTopStocks(5, recentStocksMap));
				System.out.println("Top Absolute Ask Ratio stocks are as following : ");
				printStocks(topAbsoluteAskRatioStocks.getTopStocks(5, recentStocksMap));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (StockUtil.isInMiddayNoneTradingTime()) {
				Calendar currentTime = Calendar.getInstance();
				try {
					Thread.sleep(StockUtil.AFTERNOON_START.getTimeInMillis() - currentTime.getTimeInMillis());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			} else {
				System.out.println("finished , not trading time");
				break;
			}
		}

	}

	private void printStocks(List<Stock> stockList) {
		if (stockList.size() > 0) {
			for (Stock stock : stockList) {
				System.out.println(stock.toString());
			}
		}
	}
	
}
