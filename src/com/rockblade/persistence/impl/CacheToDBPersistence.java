package com.rockblade.persistence.impl;

import static com.rockblade.cache.StockCache.ALL_STOCKS_CACHE;
import static com.rockblade.cache.StockCache.ALL_STOCK_NEED_SAVED_MARKER;
import static com.rockblade.cache.StockCache.persistenceIndexer;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;
import com.rockblade.persistence.StockPersistence;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 18, 2013 4:02:16 PM
 * 
 */

public class CacheToDBPersistence implements StockPersistence {

	private static StockPersistenceImpl jdbcManager;

	@Override
	public void saveStocks(Map<String, List<Stock>> stockList) {
		jdbcManager = new StockPersistenceImpl();
		updateCacheIndexer(stockList);

		try {
			jdbcManager.saveStockByIndex(ALL_STOCKS_CACHE, persistenceIndexer, ALL_STOCK_NEED_SAVED_MARKER);
		} catch (SQLException e) {
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
					String stockId = entry.getKey();
					if (persistenceIndexer.get(stockId) != null) {
						tempIndex[0] = persistenceIndexer.get(stockId)[1];
						tempIndex[1] = entry.getValue().size() - 1;
						if (tempIndex[0] < tempIndex[1]) {
							tempIndex[0] += 1;
							ALL_STOCK_NEED_SAVED_MARKER.put(stockId, true);
						} else {
							ALL_STOCK_NEED_SAVED_MARKER.put(stockId, false);
						}
					} else {
						tempIndex[0] = 0;
						tempIndex[1] = entry.getValue().size() - 1;
						ALL_STOCK_NEED_SAVED_MARKER.put(stockId, true);
					}
					persistenceIndexer.put(stockId, tempIndex);
				}
			}

		} else {
			// cache is empty , it's not in trading time or could not retrieve
			// data from source.
			// check if it's in rest time
		}
	}

	@Override
	public Map<String, Stock> getStockInSpecificDate(Calendar cal) {
		return null;
	}
}
