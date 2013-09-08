package com.rockblade.model;

import java.util.Date;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:23:22 AM
 * 
 */

public class Stock {

	// 股票唯一號碼
	private String stockId;
	// 股票名稱
	private String stockName;
	// 今日開盤價
	private double open;
	// 昨日收盤價
	private double preClose;
	// 當前價格
	private double currentPrice;
	// 最高價
	private double high;
	// 最低價
	private double low;
	// 競買價,即"買一"報價
	private double buy1;
	// 競賣價,即"賣一"報價
	private double sell1;
	// 成交量,即成交的股票數,使用時應除以100以轉化成手
	private long transactionVolume;
	// 成交額,即成交量的總計金額,使用時應除以一萬
	private long amount;
	// 買一申報股票數
	private long buy1Volume;
	// 買一報價
	private double buy1Price;
	// 買二申報股票數
	private long buy2Volume;
	// 買二報價
	private double buy2Price;
	// 買三申報股票數
	private long buy3Volume;
	// 買三報價
	private double buy3Price;
	// 買四申報股票數
	private long buy4Volume;
	// 買四報價
	private double buy4Price;
	// 買五申報股票數
	private long buy5Volume;
	// 買五報價
	private double buy5Price;
	// 賣一申報股票數
	private long sell1Volume;
	// 賣一報價
	private double sell1Price;
	// 賣二申報股票數
	private long sell2Volume;
	// 賣二報價
	private double sell2Price;
	// 賣三申報股票數
	private long sell3Volume;
	// 賣三報價
	private double sell3Price;
	// 賣四申報股票數
	private long sell4Volume;
	// 賣四報價
	private double sell4Price;
	// 賣五申報股票數
	private long sell5Volume;
	// 賣五報價
	private double sell5Price;
	// 日期
	private Date date;
	// 時間
	private Date time;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getPreClose() {
		return preClose;
	}

	public void setPreClose(double preClose) {
		this.preClose = preClose;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getBuy1() {
		return buy1;
	}

	public void setBuy1(double buy1) {
		this.buy1 = buy1;
	}

	public double getSell1() {
		return sell1;
	}

	public void setSell1(double sell1) {
		this.sell1 = sell1;
	}

	public long getTransactionVolume() {
		return transactionVolume;
	}

	public void setTransactionVolume(long transactionVolume) {
		this.transactionVolume = transactionVolume;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getBuy1Volume() {
		return buy1Volume;
	}

	public void setBuy1Volume(long buy1Volume) {
		this.buy1Volume = buy1Volume;
	}

	public double getBuy1Price() {
		return buy1Price;
	}

	public void setBuy1Price(double buy1Price) {
		this.buy1Price = buy1Price;
	}

	public long getBuy2Volume() {
		return buy2Volume;
	}

	public void setBuy2Volume(long buy2Volume) {
		this.buy2Volume = buy2Volume;
	}

	public double getBuy2Price() {
		return buy2Price;
	}

	public void setBuy2Price(double buy2Price) {
		this.buy2Price = buy2Price;
	}

	public long getBuy3Volume() {
		return buy3Volume;
	}

	public void setBuy3Volume(long buy3Volume) {
		this.buy3Volume = buy3Volume;
	}

	public double getBuy3Price() {
		return buy3Price;
	}

	public void setBuy3Price(double buy3Price) {
		this.buy3Price = buy3Price;
	}

	public long getBuy4Volume() {
		return buy4Volume;
	}

	public void setBuy4Volume(long buy4Volume) {
		this.buy4Volume = buy4Volume;
	}

	public double getBuy4Price() {
		return buy4Price;
	}

	public void setBuy4Price(double buy4Price) {
		this.buy4Price = buy4Price;
	}

	public long getBuy5Volume() {
		return buy5Volume;
	}

	public void setBuy5Volume(long buy5Volume) {
		this.buy5Volume = buy5Volume;
	}

	public double getBuy5Price() {
		return buy5Price;
	}

	public void setBuy5Price(double buy5Price) {
		this.buy5Price = buy5Price;
	}

	public long getSell1Volume() {
		return sell1Volume;
	}

	public void setSell1Volume(long sell1Volume) {
		this.sell1Volume = sell1Volume;
	}

	public double getSell1Price() {
		return sell1Price;
	}

	public void setSell1Price(double sell1Price) {
		this.sell1Price = sell1Price;
	}

	public long getSell2Volume() {
		return sell2Volume;
	}

	public void setSell2Volume(long sell2Volume) {
		this.sell2Volume = sell2Volume;
	}

	public double getSell2Price() {
		return sell2Price;
	}

	public void setSell2Price(double sell2Price) {
		this.sell2Price = sell2Price;
	}

	public long getSell3Volume() {
		return sell3Volume;
	}

	public void setSell3Volume(long sell3Volume) {
		this.sell3Volume = sell3Volume;
	}

	public double getSell3Price() {
		return sell3Price;
	}

	public void setSell3Price(double sell3Price) {
		this.sell3Price = sell3Price;
	}

	public long getSell4Volume() {
		return sell4Volume;
	}

	public void setSell4Volume(long sell4Volume) {
		this.sell4Volume = sell4Volume;
	}

	public double getSell4Price() {
		return sell4Price;
	}

	public void setSell4Price(double sell4Price) {
		this.sell4Price = sell4Price;
	}

	public long getSell5Volume() {
		return sell5Volume;
	}

	public void setSell5Volume(long sell5Volume) {
		this.sell5Volume = sell5Volume;
	}

	public double getSell5Price() {
		return sell5Price;
	}

	public void setSell5Price(double sell5Price) {
		this.sell5Price = sell5Price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Stock Name : " + getStockName() + " , Current Price : " + getCurrentPrice() + " , Transaction Volume : " + getTransactionVolume() / 100
				+ " Hand , Amount : " + getAmount() / 10000 + " W \n";
	}

}
