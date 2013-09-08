package com.rockblade.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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

}
