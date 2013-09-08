package com.rockblade.util;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:34:41 AM
 * 
 */

public class StockUtil {

	public enum StockURL {

		DEFAULT_STOCK_URL("http://hq.sinajs.cn/list="),
		STOCK_RESOURCES_PROPERTIES_FILE("resources/stock_resources.properties"),
		KEY_FRO_STOCK_IN_RESOURCES("stock_url"),
		SINA_SITE("hq.sinajs.cn"),
		GOOGLE_SITE("");

		private String context;

		private StockURL(String context) {
			this.context = context;
		}

		public String getContext() {
			return context;
		}

	}

}
