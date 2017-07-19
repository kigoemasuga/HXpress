package com.example.android.hxpress.managers;

import com.example.android.hxpress.models.User;

/**
 * Created by kigoe on 2017/7/19.
 */

public class UserManager {
    public static User loginedUser;
    private UserManager userManager;

    public UserManager newInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }
}
