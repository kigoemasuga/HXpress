package com.example.android.hxpress.adapters;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.hxpress.R;
import com.example.android.hxpress.fragments.square.child.PhotoFragment;
import com.example.android.hxpress.fragments.square.child.SameCityFragment;
import com.example.android.hxpress.fragments.timeline.childpager.ChatRoomFragment;
import com.example.android.hxpress.fragments.timeline.childpager.FirstPagerFragment;
import com.example.android.hxpress.fragments.timeline.childpager.OtherPagerFragment;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class SquarePagerAdapter extends FragmentPagerAdapter {
    List<String> mTabs;

    public SquarePagerAdapter(FragmentManager fm, List<String> tabs) {
        super(fm);
        mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            //同城
            return SameCityFragment.newInstance();
        } else if (position == 1) {
            //世界
            return OtherPagerFragment.newInstance(position);
        } else if (position == 2) {
            //待购
            return OtherPagerFragment.newInstance(position);
        } else if (position == 3) {
            //随手送
            return OtherPagerFragment.newInstance(position);
        } else if (position == 4) {
            //分享
            return OtherPagerFragment.newInstance(position);
        } else {
            //照片
            return PhotoFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }
}
