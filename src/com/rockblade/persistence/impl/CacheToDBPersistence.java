package com.rockblade.persistence.impl;

import static com.rockblade.cache.StockCache.ALL_STOCKS_CACHE;
import static com.rockblade.cache.StockCache.ALL_STOCK_NEED_SAVED_MARKER;
import static com.rockblade.cache.StockCache.persistenceIndexer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;
import com.rockblade.persistence.JDBCManager;
import com.rockblade.persistence.StockPersistence;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 18, 2013 4:02:16 PM
 * 
 */

public class CacheToDBPersistence implements StockPersistence {

	private static JDBCManager jdbcManager;

	@Override
	public void saveStock(Map<String, List<Stock>> stockList) {
		jdbcManager = new JDBCManager();
		updateCacheIndexer(stockList);

		try {
			jdbcManager.saveStock(ALL_STOCKS_CACHE, persistenceIndexer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateCacheIndexer(Map<String, List<Stock>> allStockMap) {

		if (!allStockMap.isEmpty()) {
			if (persistenceIndexer.isEmpty()) {
				for (Map.Entry<String, List<Stock>> entry : ALL_STOCKS_CACHE.entrySet()) {
					Integer[] tempIndex = new Integer[2];
					int entrySize = entry.getValue().size();
					tempIndex[0] = 0;
					if (entrySize < 2) {
						tempIndex[1] = 0;
					} else {
						tempIndex[1] = entry.getValue().size() - 1;
					}
					persistenceIndexer.put(entry.getKey(), tempIndex);
					ALL_STOCK_NEED_SAVED_MARKER.put(entry.getKey(), true);
				}
			} else {
				for (Map.Entry<String, List<Stock>> entry : ALL_STOCKS_CACHE.entrySet()) {
					Integer[] tempIndex = new Integer[2];
					if (persistenceIndexer.get(entry.getKey()) != null) {
						tempIndex[0] = persistenceIndexer.get(entry.getKey())[1];
						tempIndex[1] = entry.getValue().size() - 1;
						if (tempIndex[0] < tempIndex[1]) {
							tempIndex[0] += 1;
							ALL_STOCK_NEED_SAVED_MARKER.put(entry.getKey(), true);
						} else {
							ALL_STOCK_NEED_SAVED_MARKER.put(entry.getKey(), false);
						}
					} else {
						tempIndex[0] = 0;
						tempIndex[1] = 0;
						ALL_STOCK_NEED_SAVED_MARKER.put(entry.getKey(), false);
					}
					persistenceIndexer.put(entry.getKey(), tempIndex);
				}
			}

		} else {
			// cache is empty , it's not in trading time or could not retrieve
			// data from source.
			// check if it's in rest time
		}
	}

	public static void main(String... args) {
		StockIdReader stockReader = new StockIdReader();
		stockReader.readStockIdFromFile();
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

		// flush cache to DB
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (StockUtil.isInTradingTime()) {
					try {
						Thread.sleep(5 * StockUtil.MINUTE);
						cacheToDB.saveStock(StockCache.ALL_STOCKS_CACHE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
