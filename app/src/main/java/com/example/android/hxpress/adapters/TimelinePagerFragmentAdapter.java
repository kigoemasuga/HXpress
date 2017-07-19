package com.example.android.hxpress.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.hxpress.fragments.timeline.childpager.ChatRoomFragment;
import com.example.android.hxpress.fragments.timeline.childpager.FirstPagerFragment;
import com.example.android.hxpress.fragments.timeline.childpager.OtherPagerFragment;


/**
 * Created by kigoe on 16/6/5.
 */
public class TimelinePagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"订单", "接单", "聊天"};

    public TimelinePagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FirstPagerFragment.newInstance();
        } else if (position == 1) {
            return OtherPagerFragment.newInstance(position);
        } else {
            return ChatRoomFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
