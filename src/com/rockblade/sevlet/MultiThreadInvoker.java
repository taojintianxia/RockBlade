package com.rockblade.sevlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.rockblade.util.StockUtil;

public class MultiThreadInvoker {

	public void execute() {
		Map<String, String> stockMap = StockUtil.getSHStockMap();
		Set<String> keySet = stockMap.keySet();
		Iterator<String> it = keySet.iterator();
		Map<String, Map<String, Long>> stockPool = new HashMap<>(StockUtil.getSHStockMap().size());
		URLAnalyzer urlAnalyzer = new URLAnalyzer();
		while (it.hasNext()) {
			Map<String, Long> specifyStockMap = new HashMap<>();
			String stockId = it.next();
			urlAnalyzer.parseURLData("sh" + stockId, specifyStockMap);
			if (specifyStockMap.isEmpty()) {
				continue;
			}
			stockPool.put(stockId, specifyStockMap);
			System.out.println(stockId);
			StockThread stockThread = new StockThread();
			new Thread(stockThread, stockId).start();
		}
	}

}

class StockThread implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 3; i++)
			System.out.println("current thread is : " + Thread.currentThread().getName());
	}

}
