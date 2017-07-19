package com.example.android.hxpress.models;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by kigoe on 2017/7/18.
 */

public class ThingsWanted extends BmobObject {
    private String title;                   //物品标题
    private String description;             //物品描述(HTML)
    private String topImgUrls;              //顶部图片的url
    private int progressNum;                //进度信息
    private User addresser;                 //发货人
    private User recipients;                //收件人
    private User courier;                   //派送的人
    private List<Want2Buy> Want2HelpBuy;             //想帮助买的人
    private List<Want2Delivery> Want2Helpdelivery;   //想帮助派送的人
    private BmobGeoPoint point;             //坐标
    private String send2address;    //收货地址
    private String send2Name;   //收件人名称

    public String getSend2Name() {
        return send2Name;
    }

    public void setSend2Name(String send2Name) {
        this.send2Name = send2Name;
    }

    public BmobGeoPoint getPoint() {
        return point;
    }

    public void setPoint(BmobGeoPoint point) {
        this.point = point;
    }

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

    public List<Want2Buy> getWant2HelpBuy() {
        return Want2HelpBuy;
    }

    public void setWant2HelpBuy(List<Want2Buy> want2HelpBuy) {
        Want2HelpBuy = want2HelpBuy;
    }

    public String getTopImgUrls() {
        return topImgUrls;
    }

    public void setTopImgUrls(String topImgUrls) {
        this.topImgUrls = topImgUrls;
    }

    public List<Want2Delivery> getWant2Helpdelivery() {
        return Want2Helpdelivery;
    }

    public void setWant2Helpdelivery(List<Want2Delivery> want2Helpdelivery) {
        Want2Helpdelivery = want2Helpdelivery;
    }

    public String getSend2address() {
        return send2address;
    }

    public void setSend2address(String send2address) {
        this.send2address = send2address;
    }

}
