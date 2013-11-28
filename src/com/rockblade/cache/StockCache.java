package com.rockblade.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 9, 2013 1:00:24 PM
 * 
 */

public class StockCache {

    public final static int SH_STOCK_CACHE_NUM = 1000;
    public final static int ZH_STOCK_CACHE_NUM = 1500;
    
    public static Calendar persisFinishedTime = Calendar.getInstance();

    // since every fixed time , there will be some stock data stores to DB.this
    // map stores the index of every stock.
    public static Map<String, Integer[]> persistenceIndexer = new HashMap<>();

    public static final Map<String, List<Stock>> ALL_STOCKS_CACHE = new ConcurrentHashMap<>();

    public static final Map<String, Boolean> ALL_STOCK_NEED_SAVED_MARKER = new ConcurrentHashMap<>();

    public static final List<String> ALL_STOCK_ID = new ArrayList<>();

    public static void cleanCache() {
        persistenceIndexer.clear();
        ALL_STOCKS_CACHE.clear();
    }

    public static Map<String, List<Stock>> getStocksInSpecificTime(long timeInterval) {
        Map<String, List<Stock>> expectStocksMap = new HashMap<>();
        Calendar expectTime = Calendar.getInstance();
        expectTime.setTimeInMillis(expectTime.getTimeInMillis() + timeInterval);
        for (Map.Entry<String, List<Stock>> entry : ALL_STOCKS_CACHE.entrySet()) {
            String stockId = entry.getKey();
            List<Stock> stockList = entry.getValue();
            List<Stock> stocksInTimeInterval = new ArrayList<>();
            if (stockList != null) {
                for (Stock stock : stockList) {
                    if (stock.getTime().after(expectTime)) {
                        stocksInTimeInterval.add(stock);
                    }
                }
                if (!stocksInTimeInterval.isEmpty()) {
                    expectStocksMap.put(stockId, stocksInTimeInterval);
                }
            }
        }

        return expectStocksMap;
    }
}
