package com.rockblade.calculatecenter;

import java.util.LinkedList;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 3:22:20 PM
 * 
 */

public interface TopNCalculator {

	public LinkedList<Stock> getTopAmountStocks(int n);

}
