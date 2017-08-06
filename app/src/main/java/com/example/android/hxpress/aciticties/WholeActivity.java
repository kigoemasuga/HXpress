package com.example.android.hxpress.aciticties;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.android.hxpress.R;
import com.example.android.hxpress.base.BaseMainFragment;
import com.example.android.hxpress.event.TabSelectedEvent;
import com.example.android.hxpress.fragments.aboutme.AboutMeFragment;
import com.example.android.hxpress.fragments.MoreInfoFragment;
import com.example.android.hxpress.fragments.aboutme.child.LoginFragment;
import com.example.android.hxpress.fragments.square.SquareFragment;
import com.example.android.hxpress.fragments.timeline.TimeLineFragment;
import com.example.android.hxpress.view.BottomBar;
import com.example.android.hxpress.view.BottomBarTab;
import org.greenrobot.eventbus.EventBus;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class WholeActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener, LoginFragment.OnLoginSuccessListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public BottomBar mBottomBar;
    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);
        //EventBus.getDefault().register(this);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.d("ovech", "back stack changed");
                Log.d("ovech", "back stack count = " + getSupportFragmentManager().getBackStackEntryCount());
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    Log.d("ovech", "has " + getSupportFragmentManager().getBackStackEntryCount());
                } else {
                    Log.d("ovech", "hello without icon");
                }
            }
        });
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
                Log.v("WholeActivity", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
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

    @Override
    public void onLoginSuccess(String account) {
        Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
    }
}
