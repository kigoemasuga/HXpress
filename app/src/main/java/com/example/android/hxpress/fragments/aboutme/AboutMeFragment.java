package com.example.android.hxpress.fragments.aboutme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.hxpress.R;
import com.example.android.hxpress.base.BaseMainFragment;
import com.example.android.hxpress.fragments.aboutme.child.AvatarFragment;
import com.example.android.hxpress.fragments.aboutme.child.MeFragment;

/**
 * Created by kigoe on 2017/7/19.
 */

public class AboutMeFragment extends BaseMainFragment {
    private Toolbar mToolbar;
    private View mView;

    public static AboutMeFragment newInstance() {
        return new AboutMeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_aboutme, container, false);
        return mView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(AvatarFragment.class) == null) {
            loadFragment();
        }

        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar_in_aboutme);
        mToolbar.setTitle("AboutMe");
    }

    private void loadFragment() {
        loadRootFragment(R.id.fl_aboutme_container_upper, AvatarFragment.newInstance());
        loadRootFragment(R.id.fl_aboutme_container_lower, MeFragment.newInstance());
    }

    public void onBackToFirstFragment() {
        _mBackToFirstListener.onBackToFirstFragment();
    }
}
