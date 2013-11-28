package com.rockblade.model;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 11:23:22 AM
 * 
 */

public class Stock implements Cloneable {

    // 股票号码
    private String stockId;
    // 股票名称
    private String stockName;
    // 今日涨幅
    private double percent;
    // 今日开盘价
    private double open;
    // 昨日收盘价
    private double close;
    // 当前价格
    private double price;
    // 最高价
    private double high;
    // 最低价
    private double low;
    // 成交量,即成交的股票数,除以100就等于手数
    private double transactionVolume;
    // 成交额,即成交量的总计,表示时应除以10,000
    private double amount;
    // 买一申报股票数
    private double ask1Volume;
    // 买一报价
    private double ask1Price;
    // 买二申报股票數
    private double ask2Volume;
    // 买二报价
    private double ask2Price;
    // 买三申报股票數
    private double ask3Volume;
    // 买三报价
    private double ask3Price;
    // 买四申报股票數
    private double ask4Volume;
    // 买四报价
    private double ask4Price;
    // 买五申报股票數
    private double ask5Volume;
    // 买五报价
    private double ask5Price;
    // 卖一申报股票數
    private double bid1Volume;
    // 卖一报价
    private double bid1Price;
    // 卖二申报股票數
    private double bid2Volume;
    // 卖二报价
    private double bid2Price;
    // 卖三申报股票數
    private double bid3Volume;
    // 卖三报价
    private double bid3Price;
    // 卖四申报股票數
    private double bid4Volume;
    // 卖四报价
    private double bid4Price;
    // 卖五申报股票數
    private double bid5Volume;
    // 卖五报价
    private double bid5Price;
    // 时间
    private Calendar time;
    // 是否停牌
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

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public double getAsk1Volume() {
        return ask1Volume;
    }

    public void setAsk1Volume(double ask1Volume) {
        this.ask1Volume = ask1Volume;
    }

    public double getAsk1Price() {
        return ask1Price;
    }

    public void setAsk1Price(double ask1Price) {
        this.ask1Price = ask1Price;
    }

    public double getAsk2Volume() {
        return ask2Volume;
    }

    public void setAsk2Volume(double ask2Volume) {
        this.ask2Volume = ask2Volume;
    }

    public double getAsk2Price() {
        return ask2Price;
    }

    public void setAsk2Price(double ask2Price) {
        this.ask2Price = ask2Price;
    }

    public double getAsk3Volume() {
        return ask3Volume;
    }

    public void setAsk3Volume(double ask3Volume) {
        this.ask3Volume = ask3Volume;
    }

    public double getAsk3Price() {
        return ask3Price;
    }

    public void setAsk3Price(double ask3Price) {
        this.ask3Price = ask3Price;
    }

    public double getAsk4Volume() {
        return ask4Volume;
    }

    public void setAsk4Volume(double ask4Volume) {
        this.ask4Volume = ask4Volume;
    }

    public double getAsk4Price() {
        return ask4Price;
    }

    public void setAsk4Price(double ask4Price) {
        this.ask4Price = ask4Price;
    }

    public double getAsk5Volume() {
        return ask5Volume;
    }

    public void setAsk5Volume(double ask5Volume) {
        this.ask5Volume = ask5Volume;
    }

    public double getAsk5Price() {
        return ask5Price;
    }

    public void setAsk5Price(double ask5Price) {
        this.ask5Price = ask5Price;
    }

    public double getBid1Volume() {
        return bid1Volume;
    }

    public void setBid1Volume(double bid1Volume) {
        this.bid1Volume = bid1Volume;
    }

    public double getBid1Price() {
        return bid1Price;
    }

    public void setBid1Price(double bid1Price) {
        this.bid1Price = bid1Price;
    }

    public double getBid2Volume() {
        return bid2Volume;
    }

    public void setBid2Volume(double bid2Volume) {
        this.bid2Volume = bid2Volume;
    }

    public double getBid2Price() {
        return bid2Price;
    }

    public void setBid2Price(double bid2Price) {
        this.bid2Price = bid2Price;
    }

    public double getBid3Volume() {
        return bid3Volume;
    }

    public void setBid3Volume(double bid3Volume) {
        this.bid3Volume = bid3Volume;
    }

    public double getBid3Price() {
        return bid3Price;
    }

    public void setBid3Price(double bid3Price) {
        this.bid3Price = bid3Price;
    }

    public double getBid4Volume() {
        return bid4Volume;
    }

    public void setBid4Volume(double bid4Volume) {
        this.bid4Volume = bid4Volume;
    }

    public double getBid4Price() {
        return bid4Price;
    }

    public void setBid4Price(double bid4Price) {
        this.bid4Price = bid4Price;
    }

    public double getBid5Volume() {
        return bid5Volume;
    }

    public void setBid5Volume(double bid5Volume) {
        this.bid5Volume = bid5Volume;
    }

    public double getBid5Price() {
        return bid5Price;
    }

    public void setBid5Price(double bid5Price) {
        this.bid5Price = bid5Price;
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
        return "Stock Name : " + getStockName() + " , Current Price : " + getPrice() + " , Transaction Volume : " + decimalFormat.format(getTransactionVolume() / 100) + "Hand , Amount : "
                + decimalFormat.format(getAmount() / 10000) + "W" + " Yesterday Close " + getClose() + " Today Open " + getOpen() + "\n";
    }
    
    @Override
    public Stock clone() throws CloneNotSupportedException{
    	return (Stock)super.clone();
    }

}
