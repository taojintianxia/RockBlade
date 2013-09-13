package com.rockblade.invoker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.URLParser;
import com.rockblade.util.StockUtil;

public class ParserInvoker {

	final static Logger logger = LoggerFactory.getLogger(ParserInvoker.class);

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
		if (!StockCache.getSHStockIdList().isEmpty()) {
			URLParser urlParser = new URLParser();
			int stockSize = StockCache.getSHStockIdList().size();
			List<Stock> stockList = new ArrayList<>(stockSize);
			Map<String, Map<Date, Stock>> shStockMap = StockCache.getSHStockMap();
			String[] stockIdArrays = new String[stockSize];

			try {
				stockList = urlParser.getStocksByStockIds(StockCache.getSHStockIdList().toArray(stockIdArrays));
			} catch (IOException | InterruptedException | ParseException e) {
				e.printStackTrace();
			}

			for (Stock stock : stockList) {
				Map<Date, Stock> tmpStockMap = new HashMap<>();
				tmpStockMap.put(stock.getTime(), stock);
				shStockMap.put(stock.getStockId(), tmpStockMap);
			}

		} else {
			logger.error("文本文件中的ID沒有讀取出來!");
		}
	}

	public static void main(String... args) {
		long start = System.currentTimeMillis();
		ParserInvoker pi = new ParserInvoker();
		StockUtil.printMap(StockCache.getSHStockMap());
		System.out.println(((System.currentTimeMillis() - start) / 1000) + " seconds");
	}
}
