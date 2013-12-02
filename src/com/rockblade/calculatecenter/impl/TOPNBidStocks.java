package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 *
 *
 * @author Kane.Sun
 * @version Dec 2, 2013 6:21:38 PM
 * 
 */

public class TOPNBidStocks extends AbstractTopNCalculator{

	@Override
	public List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap) {
		List<Stock> topStocks = new ArrayList<>(n);
		long totalBid = 0;
		return null;
	}

}
