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
import com.example.android.hxpress.event.TabSelectedEvent;
import com.example.android.hxpress.fragments.timeline.childpager.FirstPagerFragment;
import com.example.android.hxpress.maps.ClusterClickListener;
import com.example.android.hxpress.maps.ClusterItem;
import com.example.android.hxpress.maps.ClusterOverlay;
import com.example.android.hxpress.maps.ClusterRender;
import com.example.android.hxpress.maps.RegionItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // private HomeAdapter mAdapter;
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

        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

       /* mAdapter = new HomeAdapter(_mActivity);
        /LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
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
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 3);
            Article article = new Article(mTitles[index], mContents[index]);
            articleList.add(article);
        }
        mAdapter.setDatas(articleList);

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
        });*/
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
