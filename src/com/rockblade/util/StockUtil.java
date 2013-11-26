package com.rockblade.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.rockblade.helper.PropertiesHelper;
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:34:41 AM
 * 
 */

public class StockUtil {

	public final static String SHANGHAI_STOCK_EXCHANGE = "SH";
	public final static String SHENZHEN_STOCK_EXCHANGE = "SZ";
	public final static String PROPERTIES_FILE = "resources/stock_resources.properties";
	public final static String ONLINE_API = "online_stock_parser";
	public final static String SINA_ONLINE_API = "sina_stock_url";

	public final static String ENCODING_GBK = "GBK";
	public final static String ENCODING_UTF = "UTF-8";
	
	public final static int MINUTE = 1000*60;

	static final Calendar FORENOON_START = Calendar.getInstance();
	static final Calendar FORENOON_END = Calendar.getInstance();
	static final Calendar AFTERNOON_START = Calendar.getInstance();
	static final Calendar AFTERNOON_END = Calendar.getInstance();

	static {
		initCalendar(FORENOON_START, 9, 30, 0);
		initCalendar(FORENOON_END, 11, 30, 0);
		initCalendar(AFTERNOON_START, 13, 0, 0);
		initCalendar(AFTERNOON_END, 15, 0, 0);
	}

	public static enum StockProperties {

		DEFAULT_STOCK_URL("http://hq.sinajs.cn/list="),
		STOCK_RESOURCES_PROPERTIES_FILE("resources/stock_resources.properties"),
		KEY_FRO_STOCK_IN_RESOURCES("stock_url"),
		SINA_SITE("hq.sinajs.cn"),
		GOOGLE_SITE(""),
		STOCK_ID_FILE("resources/StockId");

		private String content;

		private StockProperties(String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}
	}

	public static enum Messages {
		STOCKID_NOT_READ_FROM_TXT_FILE("股票信息沒有從文本文件中讀取出來.");

		private String content;

		private Messages(String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}
	}

	/**
	 * get the path of txt file which store the stock name-id pair
	 * 
	 * @param stockExchange
	 * @return
	 */
	public static String getStockIdListPath(String stockExchange) {

		String path = new String();

		if (Strings.isNullOrEmpty(stockExchange)) {
			throw new IllegalArgumentException();
		}

		switch (stockExchange) {
		case SHANGHAI_STOCK_EXCHANGE:
			path = "resources/StockId/SH.txt";
			break;
		case SHENZHEN_STOCK_EXCHANGE:
			path = "resources/StockId/SZ.txt";
			break;
		default:
			throw new IllegalArgumentException();
		}

		return path;
	}

	/**
	 * put the elements of map into an array , then sort by API.
	 * 
	 * @param targetMap
	 * @return
	 */

	public static <M extends Object, A extends Comparable<? super A>> Map<M, A> sortMapByValue(Map<M, A> targetMap) {
		if (targetMap.isEmpty()) {
			throw new IllegalArgumentException("Map is empty , check it!");
		}

		List<Map.Entry<M, A>> mapEntryList = new ArrayList<>(targetMap.entrySet());
		// it's merge even tim sort , better than sort by myself
		Collections.sort(mapEntryList, new Comparator<Map.Entry<M, A>>() {
			@Override
			public int compare(Map.Entry<M, A> map1, Map.Entry<M, A> map2) {
				return map1.getValue().compareTo(map2.getValue());
			}
		});

		targetMap.clear();
		for (Map.Entry<M, A> entry : mapEntryList) {
			targetMap.put(entry.getKey(), entry.getValue());
		}

		return targetMap;
	}

	public static <M extends Object, A extends Comparable<? super A>> Map<M, A> sortMapByValueInDesc(Map<M, A> targetMap) {
		if (targetMap.isEmpty()) {
			throw new IllegalArgumentException("Map is empty , check it!");
		}

		List<Map.Entry<M, A>> mapEntryList = new ArrayList<>(targetMap.entrySet());
		Collections.sort(mapEntryList, new Comparator<Map.Entry<M, A>>() {
			@Override
			public int compare(Map.Entry<M, A> map1, Map.Entry<M, A> map2) {
				return -(map1.getValue().compareTo(map2.getValue()));
			}
		});

		targetMap.clear();
		for (Map.Entry<M, A> entry : mapEntryList) {
			targetMap.put(entry.getKey(), entry.getValue());
		}

		return targetMap;
	}

	public static void printMap(Map<? extends Object, ? extends Object> targetMap) {
		if (targetMap.isEmpty()) {
			System.out.println("Map is Empty !");
		} else {
			System.out.println("------------------------");
			for (Map.Entry<? extends Object, ? extends Object> entry : targetMap.entrySet()) {
				try {
					System.out.println(entry.getKey().toString() + " : " + entry.getValue().toString());
				} catch (NullPointerException e) {
					if (entry.getKey().toString() != null) {
						System.out.println(entry.getKey().toString());
					} else if (entry.getValue().toString() != null) {
						System.out.println(entry.getKey().toString());
					} else {
						System.out.println("Null Object here ******************************************************");
					}
				}
			}
			System.out.println("------------------------");
		}
	}

	public static String getStockExchangeByStockId(String stockId) {

		String stockExchange = "";

		if (Strings.isNullOrEmpty(stockId) || stockId.length() != 6) {
			throw new IllegalArgumentException();
		}

		if (stockId.startsWith("0")) {
			stockExchange = SHENZHEN_STOCK_EXCHANGE;
		} else if (stockId.startsWith("6")) {
			stockExchange = SHANGHAI_STOCK_EXCHANGE;
		} else if (stockId.startsWith("3")) {
			// 创业板
		} else {
			throw new IllegalArgumentException();
		}

		return stockExchange;
	}

	public static String getOnlineAPIURL() {
		return PropertiesHelper.getInstance().getValue(ONLINE_API);
	}

	public static String getValue(String key) {
		return PropertiesHelper.getInstance().getValue(key);
	}

	public static void printOutToFile(List<Stock> stockList) {
		FileWriter writer;
		try {
			writer = new FileWriter("c:\\stock.txt", true);
			int stockAmount = stockList.size();
			for (int i = 0; i < stockAmount; i++) {
				Stock stock = stockList.get(i);
				writer.write(stock.toString());
				writer.write("\r\n");
				writer.flush();
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isInTradingTime() {
		boolean result = false;
		Calendar now = Calendar.getInstance();

		if (now.after(FORENOON_START) && now.before(FORENOON_END)) {
			result = true;
		}
		if (now.after(AFTERNOON_START) && now.before(AFTERNOON_END)) {
			result = true;
		}

		return true;
	}

	private static void initCalendar(Calendar cal, int date, int minute, int second) {
		cal.set(Calendar.HOUR_OF_DAY, date);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
	}
}
