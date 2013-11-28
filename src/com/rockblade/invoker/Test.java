package com.rockblade.invoker;

import java.io.IOException;

import com.rockblade.cache.StockCache;
import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;
import com.rockblade.persistence.impl.CacheToDBPersistence;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 21, 2013 9:09:22 AM
 * 
 */

public class Test {

	public static void main(String... args) throws Exception {
		// StockIdReader stockReader = new StockIdReader();
		// stockReader.readStockIdFromFile();
		StockCache.ALL_STOCK_ID.add("002024");
		// StockCache.ALL_STOCK_ID.add("600598");
		// StockCache.ALL_STOCK_ID.add("600575");
		final SinaOnlineAPIParser parser = new SinaOnlineAPIParser();
		final CacheToDBPersistence cacheToDB = new CacheToDBPersistence();
		// update cache
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					parser.updateAllStocksCache();
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}

		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (StockUtil.isInTradingTime()) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (StockCache.ALL_STOCKS_CACHE != null) {
						System.out.println("----------------size is : " + StockCache.ALL_STOCKS_CACHE.size());
					}
					if (StockCache.ALL_STOCKS_CACHE.get("002024") != null) {
						System.out.println("----------------pati : " + StockCache.ALL_STOCKS_CACHE.get("002024").size());
					}
					// if (indexerArray != null)
					// System.out.println("----------------indexer is from " +
					// indexerArray[0] + " to " + indexerArray[1]);
				}
			}
		}).start();

		// flush cache to DB
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (StockUtil.isInTradingTime()) {
						Thread.sleep(50000);
						cacheToDB.saveStocks(StockCache.ALL_STOCKS_CACHE);
						if(StockUtil.isInMiddayNoneTradingTime(StockCache.persisFinishedTime)){
							StockCache.cleanCache();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
