package com.example.android.hxpress.models;

import android.widget.Checkable;

import java.io.Serializable;
import java.util.List;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by kigoe on 2017/7/18.
 */

public class User extends BmobObject implements Serializable, Checkable {
    private String userName;
    private String userId;
    private String password;
    private List<ThingsWanted> orders;
    private int markNum;
    private double markPoint;
    private List<User> observables;
    private List<User> observer;
    private String iconImgUrl;

    public User(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ThingsWanted> getOrders() {
        return orders;
    }

    public void setOrders(List<ThingsWanted> orders) {
        this.orders = orders;
    }

    public int getMarkNum() {
        return markNum;
    }

    public void setMarkNum(int markNum) {
        this.markNum = markNum;
    }

    public double getMarkPoint() {
        return markPoint;
    }

    public void setMarkPoint(double markPoint) {
        this.markPoint = markPoint;
    }

    public List<User> getObservables() {
        return observables;
    }

    public void setObservables(List<User> observables) {
        this.observables = observables;
    }

    public List<User> getObserver() {
        return observer;
    }

    public void setObserver(List<User> observer) {
        this.observer = observer;
    }

    public String getIconImgUrl() {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl) {
        this.iconImgUrl = iconImgUrl;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void setChecked(boolean checked) {


    }

    @Override
    public void toggle() {

    }
}
