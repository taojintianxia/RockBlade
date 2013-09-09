package com.rockblade.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	// StockId , StockName
	private static Map<String, String> SHStockMap = new HashMap<>(1000);
	private static Map<String, String> ZHStockMap = new HashMap<>(1500);

	public static enum StockProperties {

		DEFAULT_STOCK_URL("http://hq.sinajs.cn/list="), STOCK_RESOURCES_PROPERTIES_FILE("resources/stock_resources.properties"), KEY_FRO_STOCK_IN_RESOURCES(
				"stock_url"), SINA_SITE("hq.sinajs.cn"), GOOGLE_SITE(""), STOCK_ID_FILE("resources/StockId");

		private String context;

		private StockProperties(String context) {
			this.context = context;
		}

		public String getContext() {
			return context;
		}

	}

	public static SimpleDateFormat getDataFormat() {
		return dateFormat;
	}

	public static SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public static Map<String, String> getSHStockMap() {
		return SHStockMap;
	}

	public static void setSHStockMap(Map<String, String> sHStockMap) {
		SHStockMap = sHStockMap;
	}

	public static Map<String, String> getZHStockMap() {
		return ZHStockMap;
	}

	public static void setZHStockMap(Map<String, String> zHStockMap) {
		ZHStockMap = zHStockMap;
	}

	/**
	 * put the elements of map into an array , then sort by API.
	 * 
	 * @param targetMap
	 * @return
	 */

	public static <M extends Object, T extends Comparable<T>> Map<M, T> sortMapByValue(Map<M, T> targetMap) {
		if (targetMap.isEmpty()) {
			throw new IllegalArgumentException("Map is empty , check it!");
		}

		List<Map.Entry<M, T>> mapEntryList = new ArrayList<>(targetMap.entrySet());
		// it's merge even tim sort , better than sort by myself
		Collections.sort(mapEntryList, new Comparator<Map.Entry<M, T>>() {
			@Override
			public int compare(Map.Entry<M, T> map1, Map.Entry<M, T> map2) {
				return map1.getValue().compareTo(map2.getValue());
			}
		});

		targetMap.clear();
		for (Map.Entry<M, T> entry : mapEntryList) {
			targetMap.put(entry.getKey(), entry.getValue());
		}

		return targetMap;
	}

}
