package com.example.android.hxpress.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.hxpress.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kigoe on 2017/7/19.
 */

public class SquareFragment extends SupportFragment {

    public static SquareFragment newInstance() {
        return new SquareFragment();
    }
    private Toolbar mToolbar;
    private RecyclerView mRecy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        initView(view);
        // 动态改动 当前Fragment的动画
        // setFragmentAnimator(fragmentAnimator);
        return view;
    }
    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mToolbar.setTitle("Square");
        mToolbar.inflateMenu(R.menu.home);
    }
}
