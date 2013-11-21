package com.rockblade.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
			List<Stock> stockList = entry.getValue();
			int start = stockMapIndexer.get(entry.getKey())[0];
			int end = stockMapIndexer.get(entry.getKey())[0];
			if (start == end && end != 0) {
				continue;
			}
			for (int i = start; i <= end; i++) {
				PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);
				transferStockToPreparedStatment(stockList.get(i), stmt);
				stmt.execute();
			}
		}

	}

	private void transferStockToPreparedStatment(Stock stock, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, stock.getStockId());
		stmt.setDouble(2, stock.getAmount());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(stock.getTime().getTime()));
		stmt.setDate(3, new Date(stock.getTime().getTimeInMillis()));
	}

}
