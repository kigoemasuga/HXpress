package com.example.android.hxpress.models;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by kigoe on 2017/7/18.
 */

public class ThingsWanted extends BmobObject {
    public static final int POST_WANTED = 634;         //发布了“欲购”的东西（待接单）
    public static final int WAIT_FOR_PAY_PRICE = 847;  //有热心人想帮忙购买（代付款）
    public static final int DEAL_WITHOUT_BUYED = 632;  //选择代购者完毕（已接单未购买）
    public static final int WAIT_FOR_EXPRESS = 2;      //代购者已购物未有人派送（待派送）
    public static final int WAIT_FOR_RECIEVE = 264;    //选择代送者完毕（待送达）
    public static final int RECEIVED_WAIT_COMMENT = 233;//订单主确定收货（待评价）
    public static final int DEAL_DONE = 667;           //订单主评价完成（交易完成）
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
    private String send2address;            //收货地址
    private String send2Name;               //收件人名称
    private BmobGeoPoint whereitnow;        //物品的当前位置
    private String whereCanBuyInAddress;    //在哪里可以买的具体地址

    public ThingsWanted() {
    }

    public ThingsWanted(String title, String description, String topImgUrls, int progressNum, User recipients, BmobGeoPoint point, String send2address, String send2Name, BmobGeoPoint whereitnow, String whereCanBuyInAddress) {
        this.title = title;
        this.description = description;
        this.topImgUrls = topImgUrls;
        this.progressNum = progressNum;
        this.recipients = recipients;
        this.point = point;
        this.send2address = send2address;
        this.send2Name = send2Name;
        this.whereitnow = whereitnow;
        this.whereCanBuyInAddress = whereCanBuyInAddress;
    }

    public String statusCode2TextInSquare(int statuCode) {
        String statusInString = "";
        switch (statuCode) {
            case POST_WANTED:
            case WAIT_FOR_PAY_PRICE:
                statusInString = "待帮购";
                break;
            case DEAL_WITHOUT_BUYED:
            case WAIT_FOR_EXPRESS:
                statusInString = "待随手送";
                break;
            case DEAL_DONE:
                statusInString = "完成订单";
            default:
                statusInString = "其他";
                break;
        }
        return statusInString;
    }

    public BmobGeoPoint getWhereitnow() {
        return whereitnow;
    }

    public void setWhereitnow(BmobGeoPoint whereitnow) {
        this.whereitnow = whereitnow;
    }

    public String getWhereCanBuyInAddress() {
        return whereCanBuyInAddress;
    }

    public void setWhereCanBuyInAddress(String whereCanBuyInAddress) {
        this.whereCanBuyInAddress = whereCanBuyInAddress;
    }

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
