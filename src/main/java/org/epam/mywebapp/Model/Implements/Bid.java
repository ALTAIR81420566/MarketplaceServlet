package org.epam.mywebapp.Model.Implements;

public class Bid {

    private Long id;
    private double count;
    private long userId;
    private long productId;

    public Bid(double count, long userId, long productId) {
        this.count = count;
        this.userId = userId;
        this.productId = productId;
    }

    public Bid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
