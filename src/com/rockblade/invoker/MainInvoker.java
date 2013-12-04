package com.rockblade.invoker;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.rockblade.cache.StockCache;
import com.rockblade.helper.StockIdReader;
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
		StockIdReader reader = new StockIdReader();
		reader.readStockIdFromFile();

		Timer updaterTimer = new Timer();
		Timer cacheTimer = new Timer();
		Timer calculationTimer = new Timer();
		Calendar cal = Calendar.getInstance();
		CacheToDB cacheToDB = new CacheToDB();
		StockAsychronousUpdater updater = new StockAsychronousUpdater();
		CalculaterCenterInvoker calInvoker = new CalculaterCenterInvoker();

		updaterTimer.schedule(updater, StockUtil.FORENOON_START.getTime());
		// cacheTimer.schedule(cacheToDB, new
		// Date(StockUtil.FORENOON_START.getTimeInMillis() + 5 *
		// StockUtil.MINUTE));
		calculationTimer.schedule(calInvoker, new Date(StockUtil.FORENOON_START.getTimeInMillis() + 5 * StockUtil.MINUTE));

		cacheTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("the stock map size is : " + StockCache.ALL_STOCKS_CACHE.size());
			}
		}, StockUtil.MINUTE);

		Thread.sleep(2000);
		if (cal.after(StockUtil.AFTERNOON_END)) {
			updaterTimer.cancel();
		} else {
			Thread.sleep(StockUtil.AFTERNOON_END.getTimeInMillis() - cal.getTimeInMillis());
			updaterTimer.cancel();
		}
	}
}
