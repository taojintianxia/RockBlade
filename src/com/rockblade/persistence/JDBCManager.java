package com.rockblade.persistence;

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

	private static Connection conn;
	private final String INSERT_STOCKDETAIL_SQL = "insert into stockdetail values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_DAILYSTOCK_SQL = "insert into dailystock values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	static {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/rockblade?" + "user=root&password=root");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void saveStocks(Map<String, List<Stock>> stockMap, Map<String, Integer[]> stockMapIndexer, Map<String, Boolean> stockSaverMarker) throws SQLException {
		for (Map.Entry<String, List<Stock>> entry : stockMap.entrySet()) {
			if (stockMapIndexer.get(entry.getKey()) != null) {
				List<Stock> stockList = entry.getValue();
				int start = stockMapIndexer.get(entry.getKey())[0];
				int end = stockMapIndexer.get(entry.getKey())[1];
				Boolean needToBeSaved = stockSaverMarker.get(entry.getKey());
				needToBeSaved = needToBeSaved == null ? false : needToBeSaved;
				if (!needToBeSaved) {
					continue;
				}

				for (int i = start; i <= end; i++) {
					PreparedStatement stmt = conn.prepareStatement(INSERT_STOCKDETAIL_SQL);
					transferStockToPreparedStatment(stockList.get(i), stmt);
					stmt.execute();
				}

				Integer[] tempIndexer = new Integer[2];
				tempIndexer[0] = tempIndexer[1] = end;
				stockMapIndexer.put(entry.getKey(), tempIndexer);
			}
		}
	}

	public void saveStock(Map<String, Stock> stockMap) throws SQLException {
		for (Map.Entry<String, Stock> entry : stockMap.entrySet()) {
			PreparedStatement prepareStatement = conn.prepareStatement(INSERT_DAILYSTOCK_SQL);
			transferStockToPreparedStatment(entry.getValue(), prepareStatement);
			prepareStatement.execute();
		}
	}

	private void transferStockToPreparedStatment(Stock stock, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, stock.getStockId());
		stmt.setString(2, stock.getStockName());
		stmt.setDouble(3, stock.getPercent());
		stmt.setDouble(4, stock.getOpen());
		stmt.setDouble(5, stock.getClose());
		stmt.setDouble(6, stock.getPrice());
		stmt.setDouble(7, stock.getHigh());
		stmt.setDouble(8, stock.getLow());
		stmt.setDouble(9, stock.getTransactionVolume());
		stmt.setDouble(10, stock.getAmount());
		stmt.setDouble(11, stock.getAsk1Volume());
		stmt.setDouble(12, stock.getAsk1Price());
		stmt.setDouble(13, stock.getAsk2Volume());
		stmt.setDouble(14, stock.getAsk2Price());
		stmt.setDouble(15, stock.getAsk3Volume());
		stmt.setDouble(16, stock.getAsk3Price());
		stmt.setDouble(17, stock.getAsk4Volume());
		stmt.setDouble(18, stock.getAsk4Price());
		stmt.setDouble(19, stock.getAsk5Volume());
		stmt.setDouble(20, stock.getAsk5Price());
		stmt.setDouble(21, stock.getBid1Volume());
		stmt.setDouble(22, stock.getBid1Price());
		stmt.setDouble(23, stock.getBid2Volume());
		stmt.setDouble(24, stock.getBid2Price());
		stmt.setDouble(25, stock.getBid3Volume());
		stmt.setDouble(26, stock.getBid3Price());
		stmt.setDouble(27, stock.getBid4Volume());
		stmt.setDouble(28, stock.getBid4Price());
		stmt.setDouble(29, stock.getBid5Volume());
		stmt.setDouble(30, stock.getBid5Price());
		stmt.setTimestamp(31, new Timestamp(stock.getTime().getTimeInMillis()));
	}

}