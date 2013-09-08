package com.rockblade.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.rockblade.util.StockUtil;

public class StockIdReader {

	@SuppressWarnings("resource")
	public void readStockIdFromFile() {
		File SHStockFile = new File(StockUtil.StockProperties.STOCK_ID_FILE.getContext() + "/SH.txt");
		File SZStockFile = new File(StockUtil.StockProperties.STOCK_ID_FILE.getContext() + "/SZ.txt");
		Map<String, String> SHStockMap = StockUtil.getSHStockMap();
		Map<String, String> SZStockMap = StockUtil.getZHStockMap();
		String readLine = "";
		String[] stockArray = new String[2];

		try {

			BufferedReader bufferedReaderForSH = new BufferedReader(new FileReader(SHStockFile));
			BufferedReader bufferedReaderForSZ = new BufferedReader(new FileReader(SZStockFile));

			while ((readLine = bufferedReaderForSH.readLine()) != null) {
				stockArray[1] = new String(readLine.substring(readLine.lastIndexOf(" ") + 1));
				stockArray[0] = new String(readLine.substring(0, readLine.lastIndexOf(" ") - 1));
				SHStockMap.put(stockArray[1], stockArray[0]);
			}

			while ((readLine = bufferedReaderForSZ.readLine()) != null) {
				stockArray = readLine.split(" ");
				SZStockMap.put(stockArray[0], stockArray[1]);
			}
		} catch (FileNotFoundException e) {
			// since even the initialization file is not exist , read from
			// Web.
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StockUtil.setSHStockMap(SHStockMap);
		StockUtil.setZHStockMap(SZStockMap);
	}

}
