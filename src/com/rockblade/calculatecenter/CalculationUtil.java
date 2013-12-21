package com.rockblade.calculatecenter;

import com.rockblade.model.Stock;

public class CalculationUtil {

	private static final Double surgedLimit = 9.9;

	public static boolean isSurgedLimit(Stock stock) {
		if (stock == null || stock.isSuspension()) {
			return false;
		}

		return stock.getPercent() >= surgedLimit;
	}

}
