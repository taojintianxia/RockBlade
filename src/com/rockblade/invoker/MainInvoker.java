package com.rockblade.invoker;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.rockblade.cache.StockCache;
import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 21, 2013 9:09:22 AM
 * 
 */

public class MainInvoker {

	public static void main(String... args) throws Exception {
		
		StockCache.ALL_STOCK_ID.add("002024");
		StockCache.ALL_STOCK_ID.add("600036");
		StockCache.ALL_STOCK_ID.add("000498");
		StockCache.ALL_STOCK_ID.add("601766");
		StockCache.ALL_STOCK_ID.add("601299");
		StockCache.ALL_STOCK_ID.add("601857");
		StockCache.ALL_STOCK_ID.add("600138");
		StockCache.ALL_STOCK_ID.add("600256");
		StockCache.ALL_STOCK_ID.add("600519");
		
		Timer updaterTimer = new Timer();
		Timer cacheTimer = new Timer();
		Timer calculationTimer = new Timer();
		Calendar cal = Calendar.getInstance();
		CacheToDB cacheToDB = new CacheToDB();
		StockAsychronousUpdater updater = new StockAsychronousUpdater();
		CalculaterCenterInvoker calInvoker = new CalculaterCenterInvoker();

		updaterTimer.schedule(updater, StockUtil.FORENOON_START.getTime());
		cacheTimer.schedule(cacheToDB, new Date(StockUtil.FORENOON_START.getTimeInMillis() + 5 * StockUtil.MINUTE));
		calculationTimer.schedule(calInvoker, new Date(StockUtil.FORENOON_START.getTimeInMillis() + 5 * StockUtil.MINUTE));

		Thread.sleep(2000);
		if (cal.after(StockUtil.AFTERNOON_END)) {
			updaterTimer.cancel();
		} else {
			Thread.sleep(StockUtil.AFTERNOON_END.getTimeInMillis() - cal.getTimeInMillis());
			updaterTimer.cancel();
		}
	}
}
