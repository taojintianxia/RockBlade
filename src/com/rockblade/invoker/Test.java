package com.rockblade.invoker;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.rockblade.util.StockUtil;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 21, 2013 9:09:22 AM
 * 
 */

public class Test {

	public static void main(String... args) throws Exception {
		Timer scheduleTimer = new Timer();
		Calendar cal = Calendar.getInstance();
		CacheToDB cacheToDB = new CacheToDB();
		StockAsychronousUpdater updater = new StockAsychronousUpdater();

		scheduleTimer.schedule(updater, StockUtil.FORENOON_START.getTime());
		scheduleTimer.schedule(cacheToDB, new Date(StockUtil.FORENOON_START.getTimeInMillis()+5*StockUtil.MINUTE));

		Thread.sleep(2000);
		if(cal.after(StockUtil.AFTERNOON_END)){
			scheduleTimer.cancel();
		}else{
			Thread.sleep(StockUtil.AFTERNOON_END.getTimeInMillis() - cal.getTimeInMillis());
			scheduleTimer.cancel();
		}
		
	}
}
