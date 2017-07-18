package com.example.android.hxpress.models;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by kigoe on 2017/7/18.
 */

public class ThingsWanted extends BmobFile {
    private String title;
    private String description;
    private List<BmobFile> images;
    private int progressNum;
    private User addresser;
    private User recipients;
    private User courier;
    private List<User> Want2HelpBuy;
    private List<User> Want2Helpdelivery;
    private double longitude;   //经度
    private double latitude ;   //纬度
    private String send2address;    //收货地址
    private String addresserName;   //收件人名称

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BmobFile> getImages() {
        return images;
    }

    public void setImages(List<BmobFile> images) {
        this.images = images;
    }

    public int getProgressNum() {
        return progressNum;
    }

    public void setProgressNum(int progressNum) {
        this.progressNum = progressNum;
    }

    public User getAddresser() {
        return addresser;
    }

    public void setAddresser(User addresser) {
        this.addresser = addresser;
    }

    public User getRecipients() {
        return recipients;
    }

    public void setRecipients(User recipients) {
        this.recipients = recipients;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public List<User> getWant2HelpBuy() {
        return Want2HelpBuy;
    }

    public void setWant2HelpBuy(List<User> want2HelpBuy) {
        Want2HelpBuy = want2HelpBuy;
    }

    public List<User> getWant2Helpdelivery() {
        return Want2Helpdelivery;
    }

    public void setWant2Helpdelivery(List<User> want2Helpdelivery) {
        Want2Helpdelivery = want2Helpdelivery;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSend2address() {
        return send2address;
    }

    public void setSend2address(String send2address) {
        this.send2address = send2address;
    }

    public String getAddresserName() {
        return addresserName;
    }

    public void setAddresserName(String addresserName) {
        this.addresserName = addresserName;
    }
}
