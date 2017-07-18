package com.example.android.hxpress.models;

/**
 * Created by kigoe on 2017/7/19.
 */

public class Want2Buy {
    private double price;
    private User BuyHelper;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getBuyHelper() {
        return BuyHelper;
    }

    public void setBuyHelper(User buyHelper) {
        BuyHelper = buyHelper;
    }
}
