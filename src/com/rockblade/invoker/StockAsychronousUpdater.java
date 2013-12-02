package com.rockblade.invoker;

import java.io.IOException;
import java.util.TimerTask;

import com.rockblade.parsecenter.impl.SinaOnlineAPIParser;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Dec 2, 2013 1:47:39 PM
 * 
 */

public class StockAsychronousUpdater extends TimerTask {

	@Override
	public void run() {
		try {
			final SinaOnlineAPIParser parser = new SinaOnlineAPIParser();
			parser.updateAllStocksCache();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
