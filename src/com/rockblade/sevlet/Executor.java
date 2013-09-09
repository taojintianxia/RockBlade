package com.rockblade.sevlet;

import com.rockblade.helper.StockIdReader;
import com.rockblade.util.StockUtil;

public class Executor {

	public static void init() {
		if (StockUtil.getSHStockMap().isEmpty() || StockUtil.getZHStockMap().isEmpty()) {
			StockIdReader stockIdReader = new StockIdReader();
			stockIdReader.readStockIdFromFile();
		}
	}
	
	public static void main(String...args){
		init();
		MultiThreadInvoker invoker = new MultiThreadInvoker();
		invoker.execute();
	}

}
