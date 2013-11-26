package com.rockblade.parsecenter.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
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

import com.rockblade.model.Stock;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:18:21 AM
 * 
 */

public class OnlineAPIParserImpl {

	final static Logger logger = LoggerFactory.getLogger(OnlineAPIParserImpl.class);

	private List<String> stockStrDataList;
	
	public void filterUnavailableStock(){
		
	}

	public String retriveURLStrDataByStockId(final String stockId) {
		String data = "";
		try {
			URL url = new URL("http://hq.sinajs.cn/list=" + stockId);
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

	private List<String> getStocksIds(final String[] targetStocks) throws IOException, InterruptedException {
		final int stocksSize = targetStocks.length;
		final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		final CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
		stockStrDataList = new ArrayList<>(stocksSize);

		httpclient.start();
		try {
			HttpGet[] requests = new HttpGet[stocksSize];
			for (int i = 0; i < stocksSize; i++) {
				requests[i] = new HttpGet(StockUtil.StockProperties.DEFAULT_STOCK_URL.getContent() + targetStocks[i]);
			}
			final CountDownLatch latch = new CountDownLatch(requests.length);
			for (final HttpGet request : requests) {
				httpclient.execute(request, new FutureCallback<HttpResponse>() {

					public void completed(final HttpResponse response) {
						latch.countDown();
						try {
							String stockData = EntityUtils.toString(response.getEntity());
							stockStrDataList.add(stockData);
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

		return stockStrDataList;
	}

	public List<Stock> getStocksByStockIds(final String[] targetStocks) throws IOException, InterruptedException, ParseException {
		List<String> stockStrList = getStocksIds(targetStocks);
		int stockSize = stockStrList.size();
		List<Stock> stockList = new ArrayList<>(stockSize);
		for (String stockStrData : stockStrList) {
			Stock stock = parseURLDataForStockAllInfo(stockStrData);
			if (!stock.isSuspension()) {
				stockList.add(stock);
			}
		}

		return stockList;
	}

	private Stock parseURLDataForStockAllInfo(String data) throws ParseException {
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
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		cal.setTime(dateFormat.parse(dataList.get(30)));
		cal.setTime(timeFormat.parse(dataList.get(31)));
		stock.setTime(cal);
		stock.setSuspension(false);

		return stock;
	}

}
