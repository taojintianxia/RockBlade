package com.rockblade.invoker;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.util.StockUtil;

public class ParserInvoker {

	public ParserInvoker() {
		init();
	}

	/**
	 * read stock name from file , if the map in cache has been initialized ,
	 * just ignore it
	 * 
	 */
	public static void init() {
		if (StockCache.getSHStockMap().isEmpty() || StockCache.getZHStockMap().isEmpty()) {
			StockIdReader stockIdReader = new StockIdReader();
			stockIdReader.readStockIdFromFile();
		}
	}
	
	

	public static void main(String... args) {
		ParserInvoker invoker = new ParserInvoker();
		StockUtil.printMap(StockCache.getSHStockMap());
	}

}
