package com.rockblade.calculatecenter;

import com.rockblade.model.Stock;

public class CalculationUtil {

	private static final Double RAISING_LIMIT = 9.9;
	private static final Double LIMIT_DOWN = -9.9;

	public static boolean isRaisingLimit(Stock stock) {
		if (stock == null || stock.isSuspension()) {
			return false;
		}

		return stock.getPercent() >= RAISING_LIMIT;
	}

	public static boolean isLimitDown(Stock stock) {
		if (stock == null || stock.isSuspension()) {
			return false;
		}

		return stock.getPercent() <= LIMIT_DOWN;
	}

}
