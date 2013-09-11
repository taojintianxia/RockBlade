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
			Map<String, Map<Date, Stock>> SHStockMap = new LinkedHashMap<>();
			Map<String, Map<Date, Stock>> SZStockMap = StockCache.getSZStockMap();

			for (Map.Entry<String, Map<Date, Stock>> entry : SHStockMap.entrySet()) {
				Map<Date, Stock> tmpMap = new HashMap<>();
				String stockStrData = urlParser.retriveURLStrDataByStockId("sh" + entry.getKey());
				Stock stock = new Stock();
				try {
					stock = urlParser.parseURLDataForStockAllInfo(stockStrData);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				tmpMap.put(stock.getTime(), stock);
				SHStockMap.put(stock.getStockId(), tmpMap);
			}

		}
	}

	public static void main(String... args) {
		ParserInvoker pi = new ParserInvoker();
		StockUtil.printMap(StockCache.getSHStockMap());
	}
}
