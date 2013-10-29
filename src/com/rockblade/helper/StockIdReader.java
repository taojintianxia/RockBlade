package com.rockblade.helper;

import static com.rockblade.util.StockUtil.SHANGHAI_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SHENZHEN_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.getStockIdListPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rockblade.cache.StockCache;

public class StockIdReader {

	final static Logger logger = LoggerFactory.getLogger(StockIdReader.class);
	private BufferedReader bufferedReaderForSH;
	private BufferedReader bufferedReaderForSZ;

	/**
	 * return the stock name-id pair which has been defined in SH.txt and SZ.txt
	 * 
	 */
	public void readStockIdFromFile() {
		File SHStockFile = new File(getStockIdListPath(SHANGHAI_STOCK_EXCHANGE));
		File SZStockFile = new File(getStockIdListPath(SHENZHEN_STOCK_EXCHANGE));
		String readLine = "";
		String[] stockArray = new String[2];

		try {

			bufferedReaderForSH = new BufferedReader(new FileReader(SHStockFile));
			bufferedReaderForSZ = new BufferedReader(new FileReader(SZStockFile));
			List<String> shStockIdList = StockCache.getSHStockIdList();
			List<String> szStockIdList = StockCache.getSZStockIdList();

			while ((readLine = bufferedReaderForSH.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				shStockIdList.add(stockArray[1]);
			}

			while ((readLine = bufferedReaderForSZ.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				szStockIdList.add(stockArray[1]);
			}

			// release the reference
			shStockIdList = null;
			szStockIdList = null;
		} catch (FileNotFoundException e) {
			// since even the initialization file is not exist , read from
			// Web.
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
