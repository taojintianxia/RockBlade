package com.rockblade.sevlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.rockblade.factory.StockServerFetchFactory;
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:18:21 AM
 * 
 */

public class URLAnalyzer {

	public static void main(String... args) {
		parseURLData("sh600016");
	}

	protected Map<String, Stock> getStockContentByStockId(String stockId) {
		Stock stock = new Stock();
		Map<String, Stock> stockMap = new HashMap<>();

		return stockMap;
	}

	private static void parseURLData(String stockId) {
		String data = "";

		try {
			URL url = new URL(StockServerFetchFactory.fetchStockURL() + stockId);
			URLConnection urlConnection = url.openConnection();
			DataInputStream dataInput = new DataInputStream(urlConnection.getInputStream());
			String inputLine;
			dataInput.readUTF();
			if ((inputLine = dataInput.readUTF()) != null) {
				data = inputLine;
			}
			dataInput.close();
		} catch (MalformedURLException me) {
			//
		} catch (IOException ioe) {
			//
		}
		System.out.println(data);
	}

}
