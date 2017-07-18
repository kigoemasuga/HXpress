package com.example.android.hxpress.aciticties;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.android.hxpress.R;
import com.example.android.hxpress.base.BaseMainFragment;
import com.example.android.hxpress.event.TabSelectedEvent;
import com.example.android.hxpress.fragments.aboutme.AboutMeFragment;
import com.example.android.hxpress.fragments.MoreInfoFragment;
import com.example.android.hxpress.fragments.SquareFragment;
import com.example.android.hxpress.fragments.TimeLineFragment;
import com.example.android.hxpress.view.BottomBar;
import com.example.android.hxpress.view.BottomBarTab;
import org.greenrobot.eventbus.EventBus;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;



public class WholeActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

   private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);
        SupportFragment firstFragment = findFragment(SquareFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = SquareFragment.newInstance();
            mFragments[SECOND] = TimeLineFragment.newInstance();
            mFragments[THIRD] = MoreInfoFragment.newInstance();
            mFragments[FOURTH] = AboutMeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(TimeLineFragment.class);
            mFragments[THIRD] = findFragment(MoreInfoFragment.class);
            mFragments[FOURTH] = findFragment(AboutMeFragment.class);
        }
        initView();
    }

    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_public_black_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_notifications_black_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_more_horiz_black_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_person_black_24dp));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof SquareFragment) {
                        currentFragment.popToChild(SquareFragment.class, false);
                    } else if (currentFragment instanceof TimeLineFragment) {
                        currentFragment.popToChild(TimeLineFragment.class, false);
                    } else if (currentFragment instanceof MoreInfoFragment) {
                        currentFragment.popToChild(MoreInfoFragment.class, false);
                    } else if (currentFragment instanceof AboutMeFragment) {
                        currentFragment.popToChild(AboutMeFragment.class, false);
                    }
                    return;
                }

                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }
}
