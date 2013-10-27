package com.rockblade.parsecenter.impl;

import static com.rockblade.util.StockUtil.ENCODING_GBK;
import static com.rockblade.util.StockUtil.SHANGHAI_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SHENZHEN_STOCK_EXCHANGE;
import static com.rockblade.util.StockUtil.SINA_ONLINE_API;
import static com.rockblade.util.StockUtil.getDataFormat;
import static com.rockblade.util.StockUtil.getOnlineAPIURL;
import static com.rockblade.util.StockUtil.getStockExchangeByStockId;
import static com.rockblade.util.StockUtil.getTimeFormat;
import static com.rockblade.util.StockUtil.getValue;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import com.rockblade.model.Stock;
import com.rockblade.parsecenter.OnlineAPIParser;

public class SinaOnlineAPIParser extends OnlineAPIParser {

	public SinaOnlineAPIParser() {
		initOnlineAPIPattern();
	}

	@Override
	public List<Stock> getStocksByIdList(List<String> stockIdList) throws InterruptedException, IOException {

		final int stocksSize = stockIdList.size();
		final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		final CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
		final List<Stock> stockList = new ArrayList<>(stocksSize);

		httpclient.start();
		try {
			HttpGet[] requests = new HttpGet[stocksSize];
			for (int i = 0; i < stocksSize; i++) {
				requests[i] = new HttpGet(getOnlineAPIURL() + stockIdList.get(i));
			}
			final CountDownLatch latch = new CountDownLatch(requests.length);
			for (final HttpGet request : requests) {
				httpclient.execute(request, new FutureCallback<HttpResponse>() {

					public void completed(final HttpResponse response) {
						latch.countDown();
						try {
							String stockData = EntityUtils.toString(response.getEntity());
							stockList.add(parseOnlineStrDataToStock(stockData));
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

		return stockList;

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

	private Stock parseOnlineStrDataToStock(String strData) {
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
		Stock testStock = parser.getStockById("600008");
		System.out.println(testStock.toString());
	}

}
