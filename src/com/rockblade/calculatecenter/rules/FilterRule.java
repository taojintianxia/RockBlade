package com.rockblade.calculatecenter.rules;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rockblade.model.Stock;

/**
 *
 *
 * @author Kane.Sun
 * @version Dec 20, 2013 11:04:34 AM
 * 
 */

public interface FilterRule {
	
	public Set<String> filter(int topN ,Set<String> orderedStockId, Map<String , List<Stock>> recentStocks);
	
}
