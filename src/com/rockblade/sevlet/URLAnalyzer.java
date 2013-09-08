package com.rockblade.sevlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			InputStreamReader inputStreamReader = new InputStreamReader(
					urlConnection.getInputStream(), "GBK");
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String inputLine;
			if ((inputLine = bufferReader.readLine()) != null) {
				data = inputLine;
			}
			bufferReader.close();
		} catch (MalformedURLException me) {
			//
		} catch (IOException ioe) {
			//
		}
		System.out.println(data);
	}
	
	private static void parseURLDataForSina(String data){
		
	}

}
