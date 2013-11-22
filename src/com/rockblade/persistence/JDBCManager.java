package com.rockblade.persistence;

import static com.rockblade.cache.StockCache.ALL_STOCK_SAVED_MARKER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.rockblade.model.Stock;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 20, 2013 1:41:53 PM
 * 
 */

public class JDBCManager {

	private Connection conn;
	private final String INSERT_SQL = "insert into stockdetail values (?,?,?)";

	{
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/rockblade?" + "user=root&password=root");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void saveStock(Map<String, List<Stock>> stockMap, Map<String, Integer[]> stockMapIndexer) throws SQLException {
		for (Map.Entry<String, List<Stock>> entry : stockMap.entrySet()) {
			if (stockMapIndexer.get(entry.getKey()) != null) {
				List<Stock> stockList = entry.getValue();
				int start = stockMapIndexer.get(entry.getKey())[0];
				int end = stockMapIndexer.get(entry.getKey())[0];
				boolean isSavedBefore = ALL_STOCK_SAVED_MARKER.get(entry.getKey());

				if (start == end && !isSavedBefore) {
					continue;
				}

				for (int i = start; i <= end; i++) {
					PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);
					transferStockToPreparedStatment(stockList.get(i), stmt);
					stmt.execute();
				}

				ALL_STOCK_SAVED_MARKER.put(entry.getKey(), true);

			}
		}

	}

	private void transferStockToPreparedStatment(Stock stock, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, stock.getStockId());
		stmt.setDouble(2, stock.getAmount());
		stmt.setTimestamp(3, new Timestamp(stock.getTime().getTimeInMillis()));
	}

}
