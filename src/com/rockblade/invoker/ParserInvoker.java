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
import com.rockblade.parsecenter.impl.OnlineAPIParserImpl;
import com.rockblade.util.StockUtil;

public class ParserInvoker {

	final static Logger logger = LoggerFactory.getLogger(ParserInvoker.class);

	public ParserInvoker() {
		try {
			initReadStockIdFromFile();
			inintALLStockMapCache();
		} catch (IOException e) {
			logger.error(StockUtil.Messages.STOCKID_NOT_READ_FROM_TXT_FILE.getContent());
		}
	}

	/**
	 * read stock name from file , if the map in cache has been initialized ,
	 * just ignore it
	 * 
	 */
	private void initReadStockIdFromFile() {
		if (StockCache.getSHStockMap().isEmpty()) {
			StockIdReader stockIdReader = new StockIdReader();
			stockIdReader.readStockIdFromFile();
		}
	}

	/**
	 * when first time invoke Parser , will retrieve stock data from online and
	 * set to StockCache
	 * 
	 * @throws IOException
	 */
	private void inintALLStockMapCache() throws IOException {
		if (StockCache.ALL_STOCK_ID.isEmpty()) {
			logger.error(StockUtil.Messages.STOCKID_NOT_READ_FROM_TXT_FILE.getContent());
			throw new IOException(StockUtil.Messages.STOCKID_NOT_READ_FROM_TXT_FILE.getContent());
		}

		if (StockCache.getSHStockMap().isEmpty()) {
			OnlineAPIParserImpl urlParser = new OnlineAPIParserImpl();
			int stockSize = StockCache.ALL_STOCK_ID.size();
			List<Stock> stockList = new ArrayList<>(stockSize);
			Map<String, Map<Date, Stock>> shStockMap = StockCache.getSHStockMap();
			String[] stockIdArrays = new String[stockSize];

			try {
				stockList = urlParser.getStocksByStockIds(StockCache.ALL_STOCK_ID.toArray(stockIdArrays));
			} catch (IOException | InterruptedException | ParseException e) {
				e.printStackTrace();
			}

			for (Stock stock : stockList) {
				// here is new HashMap , cause first time , there is not score
				// in cache
				Map<Date, Stock> tmpStockMap = new HashMap<>();
				if (!stock.isSuspension()) {
					tmpStockMap.put(stock.getTime(), stock);
					shStockMap.put(stock.getStockId(), tmpStockMap);
				}
			}
		}
	}

	/**
	 * update stock from online
	 */
	public void updateStocks() {
		OnlineAPIParserImpl urlParser = new OnlineAPIParserImpl();
		int stockSize = StockCache.ALL_STOCK_ID.size();
		List<Stock> stockList = new ArrayList<>(stockSize);
		Map<String, Map<Date, Stock>> shStockMap = StockCache.getSHStockMap();
		Map<String, Stock> currentSHStockMap = StockCache.getCurrentSHStockMap();
		String[] stockIdArrays = new String[stockSize];

		try {
			stockList = urlParser.getStocksByStockIds(StockCache.ALL_STOCK_ID.toArray(stockIdArrays));
		} catch (IOException | InterruptedException | ParseException e) {
			e.printStackTrace();
		}

		for (Stock stock : stockList) {
			// since there already some records in cache , need to get it out.
			Map<Date, Stock> tmpStockMap = shStockMap.get(stock.getStockId());
			tmpStockMap.put(new Date(), stock);
			shStockMap.put(stock.getStockId(), tmpStockMap);
			currentSHStockMap.put(stock.getStockId(), stock);
		}
	}
}
