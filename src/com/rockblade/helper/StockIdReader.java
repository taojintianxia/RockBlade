package com.rockblade.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rockblade.cache.StockCache;
import com.rockblade.util.StockUtil;

public class StockIdReader {

	final static Logger logger = LoggerFactory.getLogger(StockIdReader.class);

	@SuppressWarnings("resource")
	public void readStockIdFromFile() {
		File SHStockFile = new File(StockUtil.StockProperties.STOCK_ID_FILE.getContent() + "/SH.txt");
		File SZStockFile = new File(StockUtil.StockProperties.STOCK_ID_FILE.getContent() + "/SZ.txt");
		String readLine = "";
		String[] stockArray = new String[2];

		try {

			BufferedReader bufferedReaderForSH = new BufferedReader(new FileReader(SHStockFile));
			BufferedReader bufferedReaderForSZ = new BufferedReader(new FileReader(SZStockFile));
			List<String> shStockIdList = StockCache.getSHStockIdList();
			List<String> szStockIdList = StockCache.getSZStockIdList();

			while ((readLine = bufferedReaderForSH.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				shStockIdList.add("sh" + stockArray[1]);
			}

			while ((readLine = bufferedReaderForSZ.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				szStockIdList.add("sz" + stockArray[1]);
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
