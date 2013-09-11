package com.rockblade.invoker;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.URLParser;
import com.rockblade.util.StockUtil;

public class ParserInvoker {

	public ParserInvoker() {
		initReadStockIdFromFile();
		inintALLStockMapCache();
	}

	/**
	 * read stock name from file , if the map in cache has been initialized ,
	 * just ignore it
	 * 
	 */
	private void initReadStockIdFromFile() {
		if (StockCache.getSHStockMap().isEmpty() || StockCache.getSZStockMap().isEmpty()) {
			StockIdReader stockIdReader = new StockIdReader();
			stockIdReader.readStockIdFromFile();
		}
	}

	private void inintALLStockMapCache() {
		if (StockCache.getSHStockMap().containsValue(null) || StockCache.getSZStockMap().containsValue(null)) {
			URLParser urlParser = new URLParser();
			Map<String, Map<Date, Stock>> SHStockMap = StockCache.getSHStockMap();
			Map<String, Map<Date, Stock>> tmpSHStockMap = new LinkedHashMap<>();
			tmpSHStockMap.putAll(SHStockMap);
			Map<String, Map<Date, Stock>> SZStockMap = StockCache.getSZStockMap();
			Map<String, Map<Date, Stock>> tmpSZStockMap = new LinkedHashMap<>();
			tmpSHStockMap.putAll(SZStockMap);

			for (Map.Entry<String, Map<Date, Stock>> entry : tmpSHStockMap.entrySet()) {
				Map<Date, Stock> tmpMap = new HashMap<>();
				String stockStrData = urlParser.retriveURLStrDataByStockId("sh" + entry.getKey());
				Stock stock = new Stock();
				try {
					stock = urlParser.parseURLDataForStockAllInfo(stockStrData);
					stock.setStockId(entry.getKey());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (stock.isSuspension()) {
					SHStockMap.remove(entry.getKey());
				} else {
					tmpMap.put(stock.getTime(), stock);
					SHStockMap.put(stock.getStockId(), tmpMap);
				}
			}

			for (Map.Entry<String, Map<Date, Stock>> entry : tmpSZStockMap.entrySet()) {
				Map<Date, Stock> tmpMap = new HashMap<>();
				String stockStrData = urlParser.retriveURLStrDataByStockId("sz" + entry.getKey());
				Stock stock = new Stock();
				try {
					stock = urlParser.parseURLDataForStockAllInfo(stockStrData);
					stock.setStockId(entry.getKey());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (stock.isSuspension()) {
					SHStockMap.remove(entry.getKey());
				} else {
					tmpMap.put(stock.getTime(), stock);
					SZStockMap.put(stock.getStockId(), tmpMap);
				}
			}

		}
	}

	public static void main(String... args) {
		ParserInvoker pi = new ParserInvoker();
	}
}
