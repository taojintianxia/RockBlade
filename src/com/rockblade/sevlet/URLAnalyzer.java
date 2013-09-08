package com.rockblade.sevlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rockblade.factory.StockServerFetchFactory;
import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

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
		stock.setStockId(stockId);
		stockMap.put(stock.getTime().toString(), stock);
		return stockMap;
	}

	private static Stock parseURLData(String stockId) {
		String data = "";
		Stock stock = new Stock();
		try {
			URL url = new URL(StockServerFetchFactory.fetchStockURL() + stockId);
			URLConnection urlConnection = url.openConnection();
			InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), "GBK");
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String inputLine;
			if ((inputLine = bufferReader.readLine()) != null) {
				data = inputLine;
			}
			bufferReader.close();
			System.out.println(data);
		} catch (MalformedURLException me) {
			//
		} catch (IOException ioe) {
			//
		}
		try {
			stock = chooseParser(data);
		} catch (ParseException e) {
			// should change the formatter here
			e.printStackTrace();
		}

		System.out.println(stock.toString());
		return stock;
	}

	private static Stock chooseParser(String stockDataStr) throws ParseException {
		if (StockServerFetchFactory.fetchStockURL().contains("sina")) {
			return parseURLDataForSina(stockDataStr);
		}
		return parseURLDataForSina(stockDataStr);
	}

	private static Stock parseURLDataForSina(String data) throws ParseException {
		Stock stock = new Stock();
		String usefulData = new String(data.substring(data.indexOf("\"") + 1, data.lastIndexOf("\"")));
		List<String> dataList = Arrays.asList(usefulData.split(","));
		stock.setStockName(dataList.get(0));
		stock.setOpen(Double.parseDouble(dataList.get(1)));
		stock.setPreClose(Double.parseDouble(dataList.get(2)));
		stock.setCurrentPrice(Double.parseDouble(dataList.get(3)));
		stock.setHigh(Double.parseDouble(dataList.get(4)));
		stock.setLow(Double.parseDouble(dataList.get(5)));
		stock.setBuy1(Double.parseDouble(dataList.get(6)));
		stock.setSell1(Double.parseDouble(dataList.get(7)));
		stock.setTransactionVolume(Long.parseLong(dataList.get(8)));
		stock.setAmount(Long.parseLong(dataList.get(9)));
		stock.setBuy1Volume(Long.parseLong(dataList.get(10)));
		stock.setBuy1Price(Double.parseDouble(dataList.get(11)));
		stock.setBuy2Volume(Long.parseLong(dataList.get(12)));
		stock.setBuy2Price(Double.parseDouble(dataList.get(13)));
		stock.setBuy3Volume(Long.parseLong(dataList.get(14)));
		stock.setBuy3Price(Double.parseDouble(dataList.get(15)));
		stock.setBuy4Volume(Long.parseLong(dataList.get(16)));
		stock.setBuy4Price(Double.parseDouble(dataList.get(17)));
		stock.setBuy5Volume(Long.parseLong(dataList.get(18)));
		stock.setBuy5Price(Double.parseDouble(dataList.get(19)));
		stock.setSell1Volume(Long.parseLong(dataList.get(20)));
		stock.setSell1Price(Double.parseDouble(dataList.get(21)));
		stock.setSell2Volume(Long.parseLong(dataList.get(22)));
		stock.setSell2Price(Double.parseDouble(dataList.get(23)));
		stock.setSell3Volume(Long.parseLong(dataList.get(24)));
		stock.setSell3Price(Double.parseDouble(dataList.get(25)));
		stock.setSell4Volume(Long.parseLong(dataList.get(26)));
		stock.setSell4Price(Double.parseDouble(dataList.get(27)));
		stock.setSell5Volume(Long.parseLong(dataList.get(28)));
		stock.setSell5Price(Double.parseDouble(dataList.get(29)));
		stock.setDate(StockUtil.getDataFormat().parse(dataList.get(30)));
		stock.setTime(StockUtil.getTimeFormat().parse(dataList.get(31)));

		return stock;
	}
}
