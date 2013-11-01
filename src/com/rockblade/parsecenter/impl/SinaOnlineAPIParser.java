package com.rockblade.parsecenter.impl;

import static com.rockblade.util.StockUtil.ENCODING_GBK;
import static com.rockblade.util.StockUtil.SHANGHAI_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SHENZHEN_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SINA_ONLINE_API;
import static com.rockblade.util.StockUtil.getDataFormat;
import static com.rockblade.util.StockUtil.getStockExchangeByStockId;
import static com.rockblade.util.StockUtil.getTimeFormat;
import static com.rockblade.util.StockUtil.getValue;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.OnlineAPIParser;

public class SinaOnlineAPIParser extends OnlineAPIParser {

	private static List<Stock> stocksList = new ArrayList<>();

	public SinaOnlineAPIParser() {
		initOnlineAPIPattern();
	}

	private static class GetThread extends Thread {

		private final CloseableHttpClient httpClient;
		private final HttpContext context;
		private final HttpGet httpget;
		private final int id;

		public GetThread(CloseableHttpClient httpClient, HttpGet httpget, int id) {
			this.httpClient = httpClient;
			this.context = new BasicHttpContext();
			this.httpget = httpget;
			this.id = id;
		}

		/**
		 * Executes the GetMethod and prints some status information.
		 */
		@Override
		public void run() {
			try {
				CloseableHttpResponse response = httpClient.execute(httpget, context);
				try {
					// get the response body as an array of bytes
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						// stocksList.add(parseOnlineStrDataToStock(EntityUtils.toString(entity)));
						System.out.println(EntityUtils.toString(entity));
					}
				} finally {
					response.close();
				}
			} catch (Exception e) {
				System.out.println(id + " - error: " + e);
			}
		}

	}

	@Override
	public List<Stock> getStocksByIds(List<String> stockIdList) throws InterruptedException, IOException {

		final int stockAmount = stockIdList.size();
		String[] uriArray = new String[stockAmount];
		for (int i = 0; i < stockAmount; i++) {
			uriArray[i] = getValue(SINA_ONLINE_API) + addPrefixForStockId(stockIdList.get(i));
		}

		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);

		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
		try {
			// create an array of URIs to perform GETs on
			String[] urisToGet = uriArray;

			// create a thread for each URI
			GetThread[] threads = new GetThread[urisToGet.length];
			for (int i = 0; i < threads.length; i++) {
				HttpGet httpget = new HttpGet(urisToGet[i]);
				threads[i] = new GetThread(httpclient, httpget, i + 1);
			}

			// start the threads
			for (int j = 0; j < threads.length; j++) {
				threads[j].start();
			}

			// join the threads
			for (int j = 0; j < threads.length; j++) {
				threads[j].join();
			}

		} finally {
			httpclient.close();
		}

		return stocksList;
	}

	@Override
	public Stock getStockById(String stockId) {
		String sinaStockId = addPrefixForStockId(stockId);
		String onlineAPIStrData = getOnlineStrDataByStockId(sinaStockId, onlineAPIPattern);
		return parseOnlineStrDataToStock(onlineAPIStrData);
	}

	private void initOnlineAPIPattern() {
		onlineAPIPattern.setEncoding(ENCODING_GBK);
		onlineAPIPattern.setUrl(getValue(SINA_ONLINE_API));
	}

	private String addPrefixForStockId(String stockId) {
		String sinaStockId = "";
		if (SHANGHAI_STOCK_EXCHANGE.equals(getStockExchangeByStockId(stockId))) {
			sinaStockId = SHANGHAI_STOCK_EXCHANGE.toLowerCase() + stockId;
		} else if (SHENZHEN_STOCK_EXCHANGE.equals(getStockExchangeByStockId(stockId))) {
			sinaStockId = SHENZHEN_STOCK_EXCHANGE.toLowerCase() + stockId;
		}

		return sinaStockId;
	}

	private static Stock parseOnlineStrDataToStock(String strData) {
		Stock stock = new Stock();
		String stockId = new String(strData.substring(13, strData.indexOf("=")));
		stock.setStockId(stockId);
		String usefulData = new String(strData.substring(strData.indexOf("\"") + 1, strData.lastIndexOf("\"")));
		List<String> dataList = Arrays.asList(usefulData.split(","));
		if (dataList.size() < 3) {
			stock.setSuspension(true);
			return stock;
		}
		stock.setStockName(dataList.get(0));
		stock.setOpen(Double.parseDouble(dataList.get(1)));
		stock.setPreClose(Double.parseDouble(dataList.get(2)));
		stock.setCurrentPrice(Double.parseDouble(dataList.get(3)));
		stock.setHigh(Double.parseDouble(dataList.get(4)));
		stock.setLow(Double.parseDouble(dataList.get(5)));
		stock.setBuy1(Double.parseDouble(dataList.get(6)));
		stock.setSell1(Double.parseDouble(dataList.get(7)));
		stock.setTransactionVolume(Double.parseDouble(dataList.get(8)));
		stock.setAmount(Double.parseDouble(dataList.get(9)));
		stock.setBuy1Volume(Double.parseDouble(dataList.get(10)));
		stock.setBuy1Price(Double.parseDouble(dataList.get(11)));
		stock.setBuy2Volume(Double.parseDouble(dataList.get(12)));
		stock.setBuy2Price(Double.parseDouble(dataList.get(13)));
		stock.setBuy3Volume(Double.parseDouble(dataList.get(14)));
		stock.setBuy3Price(Double.parseDouble(dataList.get(15)));
		stock.setBuy4Volume(Double.parseDouble(dataList.get(16)));
		stock.setBuy4Price(Double.parseDouble(dataList.get(17)));
		stock.setBuy5Volume(Double.parseDouble(dataList.get(18)));
		stock.setBuy5Price(Double.parseDouble(dataList.get(19)));
		stock.setSell1Volume(Double.parseDouble(dataList.get(20)));
		stock.setSell1Price(Double.parseDouble(dataList.get(21)));
		stock.setSell2Volume(Double.parseDouble(dataList.get(22)));
		stock.setSell2Price(Double.parseDouble(dataList.get(23)));
		stock.setSell3Volume(Double.parseDouble(dataList.get(24)));
		stock.setSell3Price(Double.parseDouble(dataList.get(25)));
		stock.setSell4Volume(Double.parseDouble(dataList.get(26)));
		stock.setSell4Price(Double.parseDouble(dataList.get(27)));
		stock.setSell5Volume(Double.parseDouble(dataList.get(28)));
		stock.setSell5Price(Double.parseDouble(dataList.get(29)));
		try {
			stock.setDate(getDataFormat().parse(dataList.get(30)));
			stock.setTime(getTimeFormat().parse(dataList.get(31)));
		} catch (ParseException e) {

		}

		stock.setSuspension(false);

		return stock;

	}

	public static void main(String... args) {
		SinaOnlineAPIParser parser = new SinaOnlineAPIParser();
		stocksList.clear();
		List<Stock> stockList = new ArrayList<>();
		List<String> stockIdList = new ArrayList<>();

		StockIdReader reader = new StockIdReader();
		reader.readStockIdFromFile();
		stockIdList.addAll(StockCache.getSHStockIdList());
		try {
			stockList = parser.getStocksByIds(stockIdList);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		System.out.println(stockList.size());
		for (Stock stock : stockList) {
			System.out.print(stock.toString());
		}
	}

}
