package org.epam.mywebapp.Model.Implements;

import java.util.Date;

public class Product {

    private Long uID;
    private String description;
    private String title;
    private double startPrice;
    private Long startBiddingDate = new Date().getTime();
    private Long time;
    private double step;
    private boolean buyNow;
    private long sellerID;
    private boolean sold;

    public long getStopDate() {
        return stopDate;
    }

    private long stopDate;


    public Product(String title,String description,
                   double startPrice,double step) {
        this.description = description;
        this.title = title;
        this.startPrice = startPrice;
        this.step = step;
    }

    public Product(){

    };



    public long getTimeMillis(){
        return  time * 60 * 60 * 1000;
    }
    public void setuID(Long uID) {
        this.uID = uID;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public long getSellerID() {
        return sellerID;
    }

    public void setSellerID(long sellerID) {
        this.sellerID = sellerID;
    }

    public Long getStartBiddingDate() {
        return startBiddingDate;
    }

    public void setStartBiddingDate(Long startBiddingDate) {
        this.startBiddingDate = startBiddingDate;
    }

    public Long getuID() {
        return uID;
    }

    public void setuID(long uID) {
        this.uID = uID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public boolean isBuyNow() {
        return buyNow;
    }

    public void setIsBuyNow(boolean isBuyNow) {
        this.buyNow = isBuyNow;
    }

    public void setBuyNow(boolean buyNow) {
        this.buyNow = buyNow;
    }

    public void setTimeMillis(long aLong) {
        time = aLong / 1000 /60 / 60;
        this.stopDate = aLong + startBiddingDate;
    }

}
