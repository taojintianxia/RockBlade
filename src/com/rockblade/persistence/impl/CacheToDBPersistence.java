package com.rockblade.persistence.impl;

import static com.rockblade.cache.StockCache.ALL_STOCKS_CACHE;
import static com.rockblade.cache.StockCache.persistenceIndexer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;
import com.rockblade.persistence.JDBCManager;
import com.rockblade.persistence.StockPersistence;

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
					tempIndex[0] = 0;
					tempIndex[1] = entry.getValue().size() - 1;
					persistenceIndexer.put(entry.getKey(), tempIndex);
				}
			} else {
				for (Map.Entry<String, List<Stock>> entry : ALL_STOCKS_CACHE.entrySet()) {
					Integer[] tempIndex = new Integer[2];
					tempIndex[0] = persistenceIndexer.get(entry.getKey())[1];
					tempIndex[1] = entry.getValue().size() - 1;
					if (tempIndex[0] < tempIndex[1]) {
						tempIndex[0] += 1;
					}
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
		SinaOnlineAPIParser parser = new SinaOnlineAPIParser();

		try {
			Date targetDate = new Date();
			targetDate.setTime(System.currentTimeMillis() + 10 * 60 * 1000);
			while (new Date().before(targetDate)) {
				parser.updateAllStocksCache();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(15 * 60 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						new CacheToDBPersistence().saveStock(ALL_STOCKS_CACHE);
					}

				}).start();
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}
