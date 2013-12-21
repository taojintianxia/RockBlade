package com.rockblade.calculatecenter.rules.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rockblade.calculatecenter.CalculationUtil;
import com.rockblade.calculatecenter.rules.FilterRule;
import com.rockblade.model.Stock;

public class RaisingLimitExceptRule implements FilterRule {

	private RaisingLimitExceptRule() {

	}

	private static class LazyHolder {
		private static final RaisingLimitExceptRule INSTANCE = new RaisingLimitExceptRule();
	}

	public static RaisingLimitExceptRule getInstance() {
		return LazyHolder.INSTANCE;
	}

	@Override
	public Set<String> filter(int topN, Set<String> orderedStockId, Map<String, List<Stock>> recentStocks) {
		Set<String> topNStockId = new LinkedHashSet<>();
		for (String stockId : orderedStockId) {
			if (topNStockId.size() == topN) {
				break;
			}

			List<Stock> stockList = recentStocks.get(stockId);
			if (!CalculationUtil.isRaisingLimit(stockList.get(stockList.size() - 1))) {
				topNStockId.add(stockId);
			}
		}

		return topNStockId;
	}

}
