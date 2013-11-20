package com.rockblade.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:23:22 AM
 * 
 */

public class Stock {

	// 股票唯一号码
	private String stockId;
	// 股票名称
	private String stockName;
	// 今日开盘价
	private double open;
	// 昨日收盘价
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
	private double transactionVolume;
	// 成交額,即成交量的總計金額,使用時應除以一萬
	private double amount;
	// 買一申報股票數
	private double buy1Volume;
	// 買一報價
	private double buy1Price;
	// 買二申報股票數
	private double buy2Volume;
	// 買二報價
	private double buy2Price;
	// 買三申報股票數
	private double buy3Volume;
	// 買三報價
	private double buy3Price;
	// 買四申報股票數
	private double buy4Volume;
	// 買四報價
	private double buy4Price;
	// 買五申報股票數
	private double buy5Volume;
	// 買五報價
	private double buy5Price;
	// 賣一申報股票數
	private double sell1Volume;
	// 賣一報價
	private double sell1Price;
	// 賣二申報股票數
	private double sell2Volume;
	// 賣二報價
	private double sell2Price;
	// 賣三申報股票數
	private double sell3Volume;
	// 賣三報價
	private double sell3Price;
	// 賣四申報股票數
	private double sell4Volume;
	// 賣四報價
	private double sell4Price;
	// 賣五申報股票數
	private double sell5Volume;
	// 賣五報價
	private double sell5Price;
	// 時間
	private Calendar time;
	// 停牌
	private boolean suspension;

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

	public double getTransactionVolume() {
		return transactionVolume;
	}

	public void setTransactionVolume(double transactionVolume) {
		this.transactionVolume = transactionVolume;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBuy1Volume() {
		return buy1Volume;
	}

	public void setBuy1Volume(double buy1Volume) {
		this.buy1Volume = buy1Volume;
	}

	public double getBuy1Price() {
		return buy1Price;
	}

	public void setBuy1Price(double buy1Price) {
		this.buy1Price = buy1Price;
	}

	public double getBuy2Volume() {
		return buy2Volume;
	}

	public void setBuy2Volume(double buy2Volume) {
		this.buy2Volume = buy2Volume;
	}

	public double getBuy2Price() {
		return buy2Price;
	}

	public void setBuy2Price(double buy2Price) {
		this.buy2Price = buy2Price;
	}

	public double getBuy3Volume() {
		return buy3Volume;
	}

	public void setBuy3Volume(double buy3Volume) {
		this.buy3Volume = buy3Volume;
	}

	public double getBuy3Price() {
		return buy3Price;
	}

	public void setBuy3Price(double buy3Price) {
		this.buy3Price = buy3Price;
	}

	public double getBuy4Volume() {
		return buy4Volume;
	}

	public void setBuy4Volume(double buy4Volume) {
		this.buy4Volume = buy4Volume;
	}

	public double getBuy4Price() {
		return buy4Price;
	}

	public void setBuy4Price(double buy4Price) {
		this.buy4Price = buy4Price;
	}

	public double getBuy5Volume() {
		return buy5Volume;
	}

	public void setBuy5Volume(double buy5Volume) {
		this.buy5Volume = buy5Volume;
	}

	public double getBuy5Price() {
		return buy5Price;
	}

	public void setBuy5Price(double buy5Price) {
		this.buy5Price = buy5Price;
	}

	public double getSell1Volume() {
		return sell1Volume;
	}

	public void setSell1Volume(double sell1Volume) {
		this.sell1Volume = sell1Volume;
	}

	public double getSell1Price() {
		return sell1Price;
	}

	public void setSell1Price(double sell1Price) {
		this.sell1Price = sell1Price;
	}

	public double getSell2Volume() {
		return sell2Volume;
	}

	public void setSell2Volume(double sell2Volume) {
		this.sell2Volume = sell2Volume;
	}

	public double getSell2Price() {
		return sell2Price;
	}

	public void setSell2Price(double sell2Price) {
		this.sell2Price = sell2Price;
	}

	public double getSell3Volume() {
		return sell3Volume;
	}

	public void setSell3Volume(double sell3Volume) {
		this.sell3Volume = sell3Volume;
	}

	public double getSell3Price() {
		return sell3Price;
	}

	public void setSell3Price(double sell3Price) {
		this.sell3Price = sell3Price;
	}

	public double getSell4Volume() {
		return sell4Volume;
	}

	public void setSell4Volume(double sell4Volume) {
		this.sell4Volume = sell4Volume;
	}

	public double getSell4Price() {
		return sell4Price;
	}

	public void setSell4Price(double sell4Price) {
		this.sell4Price = sell4Price;
	}

	public double getSell5Volume() {
		return sell5Volume;
	}

	public void setSell5Volume(double sell5Volume) {
		this.sell5Volume = sell5Volume;
	}

	public double getSell5Price() {
		return sell5Price;
	}

	public void setSell5Price(double sell5Price) {
		this.sell5Price = sell5Price;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public boolean isSuspension() {
		return suspension;
	}

	public void setSuspension(boolean suspension) {
		this.suspension = suspension;
	}

	@Override
	public String toString() {
		DecimalFormat decimalFormat = new DecimalFormat(".##");
		return "Stock Name : " + getStockName() + " , Current Price : " + getCurrentPrice() + " , Transaction Volume : " + decimalFormat.format(getTransactionVolume() / 100) + "手 , Amount : "
				+ decimalFormat.format(getAmount() / 10000) + "萬" + " Yesterday Close " + getPreClose() + " Today Open " + getOpen() + "\n";
	}

}
