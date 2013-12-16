package com.rockblade.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

	public final static long MINUTE = 1000 * 60;
	public final static int TOP_NUM = 5;
	public final static int ABSOLUTE_ASK_RATIO_FACTOR = 100;

	public final static DecimalFormat decimalFormat = new DecimalFormat(".##");

	public static final Calendar FORENOON_START = Calendar.getInstance();
	public static final Calendar FORENOON_END = Calendar.getInstance();
	public static final Calendar AFTERNOON_START = Calendar.getInstance();
	public static final Calendar AFTERNOON_END = Calendar.getInstance();

	private static final Stock stock = new Stock();

	static {
		initCalendar(FORENOON_START, 9, 30, 0);
		initCalendar(FORENOON_END, 11, 30, 0);
		initCalendar(AFTERNOON_START, 13, 0, 0);
		initCalendar(AFTERNOON_END, 15, 0, 0);
		initClonableStock();
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

	public static <K extends Object, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> targetMap) {
		if (targetMap.isEmpty()) {
			return new HashMap<>();
		}

		List<Map.Entry<K, V>> mapEntryList = new ArrayList<>(targetMap.entrySet());
		Collections.sort(mapEntryList, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> map1, Map.Entry<K, V> map2) {
				return map1.getValue().compareTo(map2.getValue());
			}
		});

		targetMap.clear();
		for (Map.Entry<K, V> entry : mapEntryList) {
			targetMap.put(entry.getKey(), entry.getValue());
		}

		return targetMap;
	}

	public static <K extends Object, V extends Comparable<? super V>> Map<K, V> sortMapByValueInRevertedSequence(Map<K, V> targetMap) {
		return sortMapByValue(targetMap, false);
	}

	public static <K extends Object, V extends Comparable<? super V>> Map<K, V> sortMapByValueInSequence(Map<K, V> targetMap) {
		return sortMapByValue(targetMap, true);
	}

	private static <K extends Object, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> targetMap, final boolean order) {
		if (targetMap.isEmpty()) {
			throw new IllegalArgumentException("Map is empty , check it!");
		}

		List<Map.Entry<K, V>> mapEntryList = new ArrayList<>(targetMap.entrySet());
		Collections.sort(mapEntryList, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> map1, Map.Entry<K, V> map2) {
				if (order) {
					return (map1.getValue().compareTo(map2.getValue()));
				} else {
					return -(map1.getValue().compareTo(map2.getValue()));
				}
			}
		});

		targetMap.clear();
		for (Map.Entry<K, V> entry : mapEntryList) {
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

		return result;
	}

	public static boolean isInMiddayNoneTradingTime() {
		boolean result = false;
		Calendar now = Calendar.getInstance();
		if (now.after(FORENOON_END) && now.before(AFTERNOON_START)) {
			result = true;
		}

		return result;
	}

	public static boolean isInMiddayNoneTradingTime(Calendar cal) {
		boolean result = false;
		if (cal.after(FORENOON_END) && cal.before(AFTERNOON_START)) {
			result = true;
		}

		return result;
	}

	private static void initCalendar(Calendar cal, int date, int minute, int second) {
		cal.set(Calendar.HOUR_OF_DAY, date);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
	}

	public static Stock getInitializedBlankStock() {
		Stock clonedStock = null;
		try {
			initClonableStock();
			clonedStock = stock.clone();
			clonedStock.setTime(Calendar.getInstance());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clonedStock;
	}

	private static void initClonableStock() {
		stock.setAmount(0);
		stock.setAsk1Price(0);
		stock.setAsk1Volume(0);
		stock.setAsk2Price(0);
		stock.setAsk2Volume(0);
		stock.setAsk3Price(0);
		stock.setAsk3Volume(0);
		stock.setAsk4Price(0);
		stock.setAsk4Volume(0);
		stock.setAsk5Price(0);
		stock.setAsk5Volume(0);
		stock.setBid1Price(0);
		stock.setBid1Volume(0);
		stock.setBid2Price(0);
		stock.setBid2Volume(0);
		stock.setBid3Price(0);
		stock.setBid4Price(0);
		stock.setBid4Volume(0);
		stock.setBid5Price(0);
		stock.setBid5Volume(0);
		stock.setClose(0);
		stock.setHigh(0);
		stock.setLow(0);
		stock.setOpen(0);
		stock.setPercent(0);
		stock.setPrice(0);
		stock.setStockName("");
		stock.setSuspension(true);
		stock.setTransactionVolume(0);
		stock.setTime(Calendar.getInstance());
	}
}
