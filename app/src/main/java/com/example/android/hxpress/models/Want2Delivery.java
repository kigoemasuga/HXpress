package com.example.android.hxpress.models;

/**
 * Created by kigoe on 2017/7/19.
 */

public class Want2Delivery {
    private User DeliveryHelper;
    private double price;

    public User getDeliveryHelper() {
        return DeliveryHelper;
    }

    public void setDeliveryHelper(User deliveryHelper) {
        DeliveryHelper = deliveryHelper;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
