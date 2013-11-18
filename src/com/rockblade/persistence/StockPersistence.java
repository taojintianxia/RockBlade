package com.rockblade.persistence;

import java.util.Date;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 *
 *
 * @author Kane.Sun
 * @version Nov 18, 2013 3:53:59 PM
 * 
 */

public interface StockPersistence {

	public void saveStock(Map<String , Map<Date , Stock>> stockMap);
}
