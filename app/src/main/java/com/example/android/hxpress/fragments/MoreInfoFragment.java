package com.example.android.hxpress.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.hxpress.R;
import com.example.android.hxpress.base.BaseMainFragment;

/**
 * Created by kigoe on 2017/7/19.
 */

public class MoreInfoFragment extends BaseMainFragment {
    private Toolbar mToolbar;

    public static MoreInfoFragment newInstance() {
        return new MoreInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moreinfo, container, false);
        //initView(view);
        return view;
    }


   /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(MoreInfoFragment.class) == null) {
            loadRootFragment(R.id.fl_moreinfo_container, MoreInfoFragment.newInstance());
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
   /* private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mToolbar.setTitle("MoreInfo");
        mToolbar.inflateMenu(R.menu.home);
    }*/
}
