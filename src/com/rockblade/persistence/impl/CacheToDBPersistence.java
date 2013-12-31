package com.rockblade.persistence.impl;

import static com.rockblade.cache.StockCache.ALL_STOCKS_CACHE;
import static com.rockblade.cache.StockCache.ALL_STOCK_PERSISTENCE_IDENTIFIER;
import static com.rockblade.cache.StockCache.allStocksPersistenceIndexer;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;
import com.rockblade.persistence.StockPersistence;

/**
 * 
 * @author Kane.Sun
 * @version Nov 18, 2013 4:02:16 PM
 * 
 */

public class CacheToDBPersistence implements StockPersistence {

	private static StockPersistenceImpl jdbcManager = new StockPersistenceImpl();

	@Override
	public void saveStocks(Map<String, List<Stock>> stockList) {
		updateCacheIndexer(stockList);
		try {
			jdbcManager.saveStockByIndex(ALL_STOCKS_CACHE, allStocksPersistenceIndexer, ALL_STOCK_PERSISTENCE_IDENTIFIER);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void updateCacheIndexer(Map<String, List<Stock>> stockCache) {

		if (!stockCache.isEmpty()) {
			if (allStocksPersistenceIndexer.isEmpty()) {
				for (Map.Entry<String, List<Stock>> entry : ALL_STOCKS_CACHE.entrySet()) {
					Integer[] stockPersistenceIndexer = new Integer[2];
					stockPersistenceIndexer[0] = 0;
					stockPersistenceIndexer[1] = 0;
					
					List<Stock> tmpList = entry.getValue();
					if (!tmpList.isEmpty()) {
						stockPersistenceIndexer[1] = tmpList.size() - 1;
					}
					allStocksPersistenceIndexer.put(entry.getKey(), stockPersistenceIndexer);
					ALL_STOCK_PERSISTENCE_IDENTIFIER.put(entry.getKey(), true);
				}
			} else {
				for (Map.Entry<String, List<Stock>> entry : ALL_STOCKS_CACHE.entrySet()) {
					Integer[] tempIndex = new Integer[2];
					String stockId = entry.getKey();
					if (allStocksPersistenceIndexer.get(stockId) != null) {
						tempIndex[0] = allStocksPersistenceIndexer.get(stockId)[1];
						tempIndex[1] = entry.getValue().size() - 1;
						if (tempIndex[0] < tempIndex[1]) {
							tempIndex[0] += 1;
							ALL_STOCK_PERSISTENCE_IDENTIFIER.put(stockId, true);
						} else {
							ALL_STOCK_PERSISTENCE_IDENTIFIER.put(stockId, false);
						}
					} else {
						tempIndex[0] = 0;
						tempIndex[1] = entry.getValue().size() - 1;
						ALL_STOCK_PERSISTENCE_IDENTIFIER.put(stockId, true);
					}
					allStocksPersistenceIndexer.put(stockId, tempIndex);
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
