package com.rockblade.parsecenter.impl;

import static com.rockblade.cache.StockCache.ALL_STOCKS_CACHE;
import static com.rockblade.cache.StockCache.ALL_STOCK_ID;
import static com.rockblade.util.StockUtil.ENCODING_GBK;
import static com.rockblade.util.StockUtil.SHANGHAI_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SHENZHEN_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SINA_ONLINE_API;
import static com.rockblade.util.StockUtil.getStockExchangeByStockId;
import static com.rockblade.util.StockUtil.getValue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.rockblade.helper.StockIdReader;
import com.rockblade.model.Stock;
import com.rockblade.parsecenter.OnlineAPIParser;
import com.rockblade.util.StockUtil;

public class SinaOnlineAPIParser extends OnlineAPIParser {

	private static List<Stock> stocksList = new ArrayList<>();

	private HttpGet[] AllStocksRequests;

	// private final HttpGet[] SZStockRequests;

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
						String stockStrData = EntityUtils.toString(entity);
						Stock stock = parseOnlineStrDataToStock(stockStrData);
						if (stock != null) {
							stocksList.add(stock);
						} 
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
			stock = null;
			ALL_STOCK_ID.remove(stockId);
			return stock;
		}

		stock.setStockName(dataList.get(0));
		stock.setOpen(Double.parseDouble(dataList.get(1)));
		stock.setClose(Double.parseDouble(dataList.get(2)));
		stock.setPrice(Double.parseDouble(dataList.get(3)));
		stock.setHigh(Double.parseDouble(dataList.get(4)));
		stock.setLow(Double.parseDouble(dataList.get(5)));
		stock.setTransactionVolume(Double.parseDouble(dataList.get(8)));
		stock.setAmount(Double.parseDouble(dataList.get(9)));
		stock.setAsk1Volume(Double.parseDouble(dataList.get(10)));
		stock.setAsk1Price(Double.parseDouble(dataList.get(11)));
		stock.setAsk2Volume(Double.parseDouble(dataList.get(12)));
		stock.setAsk2Price(Double.parseDouble(dataList.get(13)));
		stock.setAsk3Volume(Double.parseDouble(dataList.get(14)));
		stock.setAsk3Price(Double.parseDouble(dataList.get(15)));
		stock.setAsk4Volume(Double.parseDouble(dataList.get(16)));
		stock.setAsk4Price(Double.parseDouble(dataList.get(17)));
		stock.setAsk5Volume(Double.parseDouble(dataList.get(18)));
		stock.setAsk5Price(Double.parseDouble(dataList.get(19)));
		stock.setBid1Volume(Double.parseDouble(dataList.get(20)));
		stock.setBid1Price(Double.parseDouble(dataList.get(21)));
		stock.setBid2Volume(Double.parseDouble(dataList.get(22)));
		stock.setBid2Price(Double.parseDouble(dataList.get(23)));
		stock.setBid3Volume(Double.parseDouble(dataList.get(24)));
		stock.setBid3Price(Double.parseDouble(dataList.get(25)));
		stock.setBid4Volume(Double.parseDouble(dataList.get(26)));
		stock.setBid4Price(Double.parseDouble(dataList.get(27)));
		stock.setBid5Volume(Double.parseDouble(dataList.get(28)));
		stock.setBid5Price(Double.parseDouble(dataList.get(29)));
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cal.setTime(formatter.parse(dataList.get(30) + " " + dataList.get(31)));
			stock.setTime(cal);
		} catch (ParseException e) {
			System.out.println(e);
		}

		if (stock.getOpen() == 0) {
			stock.setSuspension(true);
		}

		if (stock.getOpen() != 0) {
			DecimalFormat decimalFormat = new DecimalFormat(".##");
			Double percent = Double.parseDouble(decimalFormat.format((stock.getPrice() - stock.getClose()) / stock.getClose() * 100));
			stock.setPercent(percent);
			stock.setSuspension(false);
		} else {
			stock.setPercent(0);
			stock.setSuspension(true);
		}

		return stock;

	}

	public void updateAllStocksCache() throws InterruptedException, IOException {
		int stockAmount = ALL_STOCK_ID.size();
		AllStocksRequests = new HttpGet[stockAmount];
		for (int i = 0; i < stockAmount; i++) {
			AllStocksRequests[i] = new HttpGet(getValue(SINA_ONLINE_API) + addPrefixForStockId(ALL_STOCK_ID.get(i)));
		}

		final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		final CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();

		httpclient.start();
		try {
			while (true) {
				if (StockUtil.isInTradingTime()) {

					Thread.sleep(3000);
					final HttpGet[] requests = AllStocksRequests;
					final CountDownLatch latch = new CountDownLatch(requests.length);
					for (final HttpGet request : requests) {
						httpclient.execute(request, new FutureCallback<HttpResponse>() {

							public void completed(final HttpResponse response) {
								latch.countDown();
								try {
									String stockStrData = EntityUtils.toString(response.getEntity());
									// System.out.println(stockStrData);
									Stock stock = parseOnlineStrDataToStock(stockStrData);
									// System.out.println("current price is : "
									// +stock.getPrice());
									if (stock == null) {
										return;
									}
									// first time get the stock info
									if (ALL_STOCKS_CACHE.get(stock.getStockId()) == null) {
										ArrayList<Stock> singleStockList = new ArrayList<>(1);
										singleStockList.add(stock);
										ALL_STOCKS_CACHE.put(stock.getStockId(), singleStockList);
									} else {
										int stockAmount = ALL_STOCKS_CACHE.get(stock.getStockId()).size();
										Stock lastStock = ALL_STOCKS_CACHE.get(stock.getStockId()).get(stockAmount - 1);
										if (lastStock.getPrice() != stock.getPrice() && lastStock.getTime().before(stock.getTime())) {
											ALL_STOCKS_CACHE.get(stock.getStockId()).add(stock);
										}
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							public void failed(final Exception ex) {
								latch.countDown();
								System.out.println(request.getRequestLine() + "->" + ex);
							}

							public void cancelled() {
								latch.countDown();
								System.out.println(request.getRequestLine() + " cancelled");
							}

						});
					}
					latch.await();
					System.out.println("Shutting down");
				} else if (StockUtil.isInMiddayNoneTradingTime()) {
					Calendar currentTime = Calendar.getInstance();
					Thread.sleep(StockUtil.AFTERNOON_START.getTimeInMillis() - currentTime.getTimeInMillis());
				} else {
					System.out.println("finished , not trading time");
					break;
				}
			}
		} finally {
			httpclient.close();
		}
		System.out.println("Done");

	}

	public static void main(String... args) {
		SinaOnlineAPIParser parser = new SinaOnlineAPIParser();
		StockIdReader reader = new StockIdReader();
		reader.readStockIdFromFile();
		Date targetDate = new Date();
		List<Stock> stockList = new ArrayList<>();
		targetDate.setTime(System.currentTimeMillis() + 10 * 60 * 1000);
		while (new Date().before(targetDate)) {
			try {
				stockList = parser.getStocksByIds(ALL_STOCK_ID);
				System.out.println("==========stock list size : " + stockList.size() + "==========");
				StockUtil.printOutToFile(stockList);
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}

	}

}
