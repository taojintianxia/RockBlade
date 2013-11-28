package com.rockblade.calculatecenter.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rockblade.calculatecenter.TopNCalculator;
import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 4:55:03 PM
 * 
 */

public abstract class AbstractTopNCalculator implements TopNCalculator {

	protected int n;

	public AbstractTopNCalculator(int n) {
		this.n = n;
	}

	@Override
	public abstract List<Stock> getTopStocks(int n, Map<String, List<Stock>> stocksMap);

}
