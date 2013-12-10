package com.rockblade.invoker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;
import com.rockblade.persistence.impl.StockPersistenceImpl;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 5, 2013 2:49:15 PM
 * 
 */

public class StockDailyRecord extends TimerTask {

	@Override
	public void run() {
		StockIdReader reader = new StockIdReader();
		reader.readStockIdFromFile();
		Map<String, Stock> testMap = new HashMap<>();
		StockPersistenceImpl persis = new StockPersistenceImpl();
		SinaOnlineAPIParser parser = new SinaOnlineAPIParser();
		List<Stock> stocksList = new ArrayList<>();
		List<String> testList = new ArrayList<>();
		try {
			stocksList = parser.getStocksByIds(StockCache.ALL_STOCK_ID);
			for (Stock stock : stocksList) {
				testMap.put(stock.getStockId(), stock);
			}
			persis.saveStock(testMap);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		StockDailyRecord re = new StockDailyRecord();
		re.run();
	}

}
