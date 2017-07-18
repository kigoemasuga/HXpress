package com.example.android.hxpress.services;

import com.example.android.hxpress.models.User;

/**
 * Created by kigoe on 2017/7/18.
 */

public class UserManager {
    public static User loginedUser;
    private UserManager userManager;

    public UserManager getInstance(){
        if(userManager==null){
            userManager= new UserManager();
        }
        return userManager;
    }
}
