package com.rockblade.parsecenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class URLParser {

	final static Logger logger = LoggerFactory.getLogger(URLParser.class);

	public String retriveURLStrDataByStockId(final String stockId) {
		String data = "";
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
		} catch (MalformedURLException me) {
			//
		} catch (IOException ioe) {
			//
		}

		return data;
	}

	private void testPrint(String str) {
		System.out.println(str);
	}

	private List<String> getStocksIds(final String[] targetStocks) throws IOException, InterruptedException {

		final int stocksSize = targetStocks.length;
		final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		final CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
		final List<String> stockStrList = new ArrayList<>(stocksSize);
		final List<Stock> stockList = new CopyOnWriteArrayList<>();
		httpclient.start();
		try {
			HttpGet[] requests = new HttpGet[stocksSize];
			for (int i = 0; i < stocksSize; i++) {
				requests[i] = new HttpGet(StockUtil.StockProperties.DEFAULT_STOCK_URL.getContext() + targetStocks[i]);
			}
			final CountDownLatch latch = new CountDownLatch(requests.length);
			for (final HttpGet request : requests) {
				httpclient.execute(request, new FutureCallback<HttpResponse>() {

					public void completed(final HttpResponse response) {
						latch.countDown();
						try {
							String stockData = EntityUtils.toString(response.getEntity());
							System.out.println(stockData);
							stockStrList.add(stockData);
						} catch (IOException | org.apache.http.ParseException e) {
							logger.error(e.getLocalizedMessage());
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
		} finally {
			httpclient.close();
		}

		return stockStrList;
	}

	public void testtest(String data, List<Stock> stockList) {
		System.out.println(data);
		Stock stock = new Stock();
		String stockId = new String(data.substring(13, data.indexOf("=")));
		stock.setStockId(stockId);
		String usefulData = new String(data.substring(data.indexOf("\"") + 1, data.lastIndexOf("\"")));
		List<String> dataList = Arrays.asList(usefulData.split(","));
		if (dataList.size() < 3) {
			stock.setSuspension(true);
		} else {
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
				stock.setDate(StockUtil.getDataFormat().parse(dataList.get(30)));
				stock.setTime(StockUtil.getTimeFormat().parse(dataList.get(31)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stock.setSuspension(false);
		}

		stockList.add(stock);

	}

	public List<Stock> getStocksByStockIds(final String[] targetStocks) throws IOException, InterruptedException, ParseException {
		List<String> stockStrList = getStocksIds(targetStocks);
		int stockSize = stockStrList.size();
		List<Stock> stockList = new ArrayList<>(stockStrList.size());
		for (String stockStrData : stockStrList) {
			Stock stock = parseURLDataForStockAllInfo(stockStrData);
			if (!stock.isSuspension()) {
				stockList.add(stock);
			}
		}

		return stockList;
	}

	public Stock parseURLDataForStockAllInfo(String data) throws ParseException {
		System.out.println(data);
		Stock stock = new Stock();
		String stockId = new String(data.substring(13, data.indexOf("=")));
		stock.setStockId(stockId);
		String usefulData = new String(data.substring(data.indexOf("\"") + 1, data.lastIndexOf("\"")));
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
		stock.setDate(StockUtil.getDataFormat().parse(dataList.get(30)));
		stock.setTime(StockUtil.getTimeFormat().parse(dataList.get(31)));
		stock.setSuspension(false);

		return stock;
	}

}
