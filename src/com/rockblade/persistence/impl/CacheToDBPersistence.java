package com.rockblade.persistence.impl;

import java.util.Date;
import java.util.Map;

import com.rockblade.model.Stock;
import com.rockblade.persistence.StockPersistence;

/**
 *
 *
 * @author Kane.Sun
 * @version Nov 18, 2013 4:02:16 PM
 * 
 */

public class CacheToDBPersistence implements StockPersistence{

	@Override
	public void saveStock(Map<String, Map<Date, Stock>> stockMap) {
		
	}

}
