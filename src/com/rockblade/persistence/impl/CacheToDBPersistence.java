package com.rockblade.persistence.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;
import com.rockblade.persistence.StockPersistence;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 18, 2013 4:02:16 PM
 * 
 */

public class CacheToDBPersistence implements StockPersistence {

	@Override
	public void saveStock(Map<String, Map<Date, Stock>> stockMap) {
		if (stockMap.isEmpty()) {
			// cache is empty , it's in rest time or could not retrieve data
			// from source.
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
				System.out.println(StockCache.ALL_STOCKS_CACHE.size());
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}
