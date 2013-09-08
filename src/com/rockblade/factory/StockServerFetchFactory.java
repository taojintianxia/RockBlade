package com.rockblade.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:32:17 AM
 * 
 */

public class StockServerFetchFactory {

	public static void main(String... args) {

		System.out.println(fetchStockURL());
	}

	/**
	 * get stock server from properties file.if file is not existed , set a
	 * default server
	 * 
	 * @return
	 */
	public static String fetchStockURL() {
		String stockURL;
		Properties configProperties = new Properties();
		File configFile = new File(StockUtil.StockProperties.STOCK_RESOURCES_PROPERTIES_FILE.getContext());

		try {
			configProperties.load(new FileInputStream(configFile));
			stockURL = configProperties.getProperty(StockUtil.StockProperties.KEY_FRO_STOCK_IN_RESOURCES.getContext());
		} catch (IOException e) {
			// should be replaced with slf4j logback
			stockURL = StockUtil.StockProperties.DEFAULT_STOCK_URL.getContext();
			e.printStackTrace();
		}

		return stockURL;
	}
}
