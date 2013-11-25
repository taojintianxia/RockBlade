package com.rockblade.persistence.impl;

import java.io.IOException;
import java.util.List;

import com.rockblade.cache.StockCache;
import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;
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
		StockCache.ALL_STOCK_ID.add("600598");
		StockCache.ALL_STOCK_ID.add("600575");
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
					List stockList = StockCache.ALL_STOCKS_CACHE.get("600575");
					Integer[] indexerArray = StockCache.persistenceIndexer.get("600575");
					if (stockList != null)
						System.out.println("----------------size is : " + StockCache.ALL_STOCKS_CACHE.get("600575").size());
					if (indexerArray != null)
						System.out.println("----------------indexer is from " + indexerArray[0] + " to " + indexerArray[1]);
				}
			}

		}).start();

		// flush cache to DB
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (StockUtil.isInTradingTime()) {
					cacheToDB.saveStock(StockCache.ALL_STOCKS_CACHE);
				}
			}
		}).start();
	}
}
