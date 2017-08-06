package com.example.android.hxpress.fragments.square.child;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.example.android.hxpress.R;
import com.example.android.hxpress.aciticties.WholeActivity;
import com.example.android.hxpress.adapters.WantedThingAdapterInSquare;
import com.example.android.hxpress.event.TabSelectedEvent;
import com.example.android.hxpress.fragments.timeline.DetailFragment;
import com.example.android.hxpress.listener.OnItemClickListener;
import com.example.android.hxpress.managers.UserManager;
import com.example.android.hxpress.maps.ClusterClickListener;
import com.example.android.hxpress.maps.ClusterItem;
import com.example.android.hxpress.maps.ClusterOverlay;
import com.example.android.hxpress.maps.ClusterRender;
import com.example.android.hxpress.maps.RegionItem;
import com.example.android.hxpress.models.ThingsWanted;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.datatype.BmobGeoPoint;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by kigoe on 2017/7/20.
 */

public class SameCityFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener, ClusterRender,
        AMap.OnMapLoadedListener, ClusterClickListener {
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private MapView mMapView;
    private AMap mAMap;
    private int clusterRadius = 100;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();
    private ClusterOverlay mClusterOverlay;
    private View view;
    private WantedThingAdapterInSquare mAdapter;
    private boolean mAtTop = true;
    private int mScrollTotal;


    public static SameCityFragment newInstance() {

        Bundle args = new Bundle();

        SameCityFragment fragment = new SameCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeline_pager_first, container, false);
        EventBus.getDefault().register(this);

        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        initView(view);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new WantedThingAdapterInSquare(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 这里的DetailFragment在flow包里
                // 这里是父Fragment启动,要注意 栈层级
                ((SupportFragment) getParentFragment()).start(DetailFragment.newInstance(mAdapter.getItem(position).getTitle()));
            }
        });

        // Init Datas
        List<ThingsWanted> thingsWantedList = new ArrayList<>();
        ThingsWanted thingsWanted1 = new ThingsWanted("跪求懷柔軍訓基地酸奶", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "懷柔軍訓基地");
        ThingsWanted thingsWanted2 = new ThingsWanted("跪求後海鷄尾酒", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                ThingsWanted.WAIT_FOR_PAY_PRICE, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "後海酒吧");
        ThingsWanted thingsWanted3 = new ThingsWanted("跪求鼓樓大街冰糖胡", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=35&spn=0&di=34795071522&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935%2C1468584406&os=4066135448%2C2311833699&simid=3390486262%2C421558439&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bks52k7f_z%26e3Bv54AzdH3Fs52-s52fAzdH3Fncbcambb_z%26e3Bip4s&gsm=5a&rpstart=0&rpnum=0",
                ThingsWanted.DEAL_WITHOUT_BUYED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "鼓樓大街");
        ThingsWanted thingsWanted4 = new ThingsWanted("跪求北京動物園熊貓一只", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=167&spn=0&di=287432865072&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1775454729%2C1998449072&os=3423699580%2C3208303626&simid=4138668375%2C732456788&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&oriquery=&objurl=http%3A%2F%2Fimg.tradekey.com%2Fimages%2Fuploadedimages%2Foffers%2F3%2F2%2FA1660744-20080513094932.jpg&gsm=ab&rpstart=0&rpnum=0",
                ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京動物園");
        ThingsWanted thingsWanted5 = new ThingsWanted("跪求後海鷄尾酒", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                ThingsWanted.WAIT_FOR_RECIEVE, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "後海就把");
        ThingsWanted thingsWanted6 = new ThingsWanted("跪求後海鷄尾酒", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                ThingsWanted.RECEIVED_WAIT_COMMENT, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "後海就把");
        ThingsWanted thingsWanted7 = new ThingsWanted("跪求後海鷄尾酒", "來個人幫我賣一賣吧~",
                "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                ThingsWanted.DEAL_DONE, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "後海就把");

        thingsWantedList.add(thingsWanted1);
        thingsWantedList.add(thingsWanted2);
        thingsWantedList.add(thingsWanted3);
        thingsWantedList.add(thingsWanted4);
        thingsWantedList.add(thingsWanted5);
        thingsWantedList.add(thingsWanted6);
        thingsWantedList.add(thingsWanted7);

        mAdapter.setDatas(thingsWantedList);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mAtTop = true;
                } else {
                    mAtTop = false;
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        //初始化地图导航上的点
        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView.getMap();
            mAMap.setOnMapLoadedListener(this);
            //点击可以动态添加点
            mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    double lat = Math.random() + 39.474923;
                    double lon = Math.random() + 116.027116;

                    LatLng latLng1 = new LatLng(lat, lon, false);
                    RegionItem regionItem = new RegionItem(latLng1, "test");
                    mClusterOverlay.addClusterItem(regionItem);

                }
            });
        }


    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != WholeActivity.SECOND) return;
        if (mAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onMapLoaded() {
        //添加测试数据
        new Thread() {
            public void run() {
                List<ClusterItem> items = new ArrayList<ClusterItem>();

                //随机10000个点
                for (int i = 0; i < 10000; i++) {

                    double lat = Math.random() + 39.474923;
                    double lon = Math.random() + 116.027116;

                    LatLng latLng = new LatLng(lat, lon, false);
                    RegionItem regionItem = new RegionItem(latLng,
                            "test" + i);
                    items.add(regionItem);

                }
                mClusterOverlay = new ClusterOverlay(mAMap, items,
                        dp2px(view.getContext(), clusterRadius),
                        view.getContext());
                mClusterOverlay.setClusterRenderer(SameCityFragment.this);
                mClusterOverlay.setOnClusterClickListener(SameCityFragment.this);

            }

        }
                .start();
    }


    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ClusterItem clusterItem : clusterItems) {
            builder.include(clusterItem.getPosition());
        }
        LatLngBounds latLngBounds = builder.build();
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0)
        );
    }

    @Override
    public Drawable getDrawAble(int clusterNum) {
        int radius = dp2px(view.getContext(), 80);
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                bitmapDrawable = view.getResources().getDrawable(
                        R.drawable.icon_openmap_mark);
                mBackDrawAbles.put(1, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 5) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(159, 210, 154, 6)));
                mBackDrawAbles.put(2, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(199, 217, 114, 0)));
                mBackDrawAbles.put(3, bitmapDrawable);
            }

            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(235, 215, 66, 2)));
                mBackDrawAbles.put(4, bitmapDrawable);
            }

            return bitmapDrawable;
        }
    }

    private Bitmap drawCircle(int radius, int color) {

        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
