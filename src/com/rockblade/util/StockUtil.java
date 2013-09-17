package com.rockblade.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:34:41 AM
 * 
 */

public class StockUtil {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public static enum StockProperties {

		DEFAULT_STOCK_URL("http://hq.sinajs.cn/list="), STOCK_RESOURCES_PROPERTIES_FILE("resources/stock_resources.properties"), KEY_FRO_STOCK_IN_RESOURCES(
				"stock_url"), SINA_SITE("hq.sinajs.cn"), GOOGLE_SITE(""), STOCK_ID_FILE("resources/StockId");

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

	public static SimpleDateFormat getDataFormat() {
		return dateFormat;
	}

	public static SimpleDateFormat getTimeFormat() {
		return timeFormat;
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
		// it's merge even tim sort , better than sort by myself
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
}
