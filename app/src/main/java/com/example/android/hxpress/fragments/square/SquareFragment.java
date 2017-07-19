package com.example.android.hxpress.fragments.square;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.hxpress.R;
import com.example.android.hxpress.adapters.SquarePagerAdapter;
import com.example.android.hxpress.base.BaseMainFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by kigoe on 2017/7/19.
 */

public class SquareFragment extends BaseMainFragment {

    private static final String[] CHANNELS = new String[]{"    同城    ", "   世界    ", "    欲购    ", "    随手送    ", "    分享    ", "    照片    "};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    MagicIndicator magicIndicator;
    private Toolbar mToolbar;
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private SquarePagerAdapter mSquarePagerAdapter;
    private ViewPager mViewPager;

    public static SquareFragment newInstance() {
        return new SquareFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        initView(view);
        // 动态改动 当前Fragment的动画// setFragmentAnimator(fragmentAnimator);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mToolbar.setTitle("Square");
        mToolbar.inflateMenu(R.menu.home);
        mSquarePagerAdapter = new SquarePagerAdapter(getChildFragmentManager(), mDataList);
        magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSquarePagerAdapter);
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.15f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
                indicator.setLineColor(Color.parseColor("#e94220"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
