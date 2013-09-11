package com.rockblade.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.rockblade.cache.StockCache;
import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

public class StockIdReader {

	@SuppressWarnings("resource")
	public void readStockIdFromFile() {
		File SHStockFile = new File(StockUtil.StockProperties.STOCK_ID_FILE.getContext() + "/SH.txt");
		File SZStockFile = new File(StockUtil.StockProperties.STOCK_ID_FILE.getContext() + "/SZ.txt");
		Map<String, Map<Date, Stock>> SHStockMap = StockCache.getSHStockMap();
		Map<String, Map<Date, Stock>> SZStockMap = StockCache.getSZStockMap();
		String readLine = "";
		String[] stockArray = new String[2];

		try {

			BufferedReader bufferedReaderForSH = new BufferedReader(new FileReader(SHStockFile));
			BufferedReader bufferedReaderForSZ = new BufferedReader(new FileReader(SZStockFile));

			while ((readLine = bufferedReaderForSH.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				SHStockMap.put(stockArray[1], null);
			}

			while ((readLine = bufferedReaderForSZ.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				SZStockMap.put(stockArray[1], null);
			}
		} catch (FileNotFoundException e) {
			// since even the initialization file is not exist , read from
			// Web.
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StockCache.setSHStockMap(SHStockMap);
		StockCache.setSZStockMap(SZStockMap);
	}

}
