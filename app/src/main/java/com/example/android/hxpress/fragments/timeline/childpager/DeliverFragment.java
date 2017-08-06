package com.example.android.hxpress.fragments.timeline.childpager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hxpress.R;
import com.example.android.hxpress.aciticties.WholeActivity;
import com.example.android.hxpress.event.TabSelectedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.gujun.android.taggroup.TagGroup;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kigoe on 16/6/3.
 */
public class DeliverFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.tag_group)
    TagGroup tagGroup;
    @BindView(R.id.title_textview)
    TextView titleTextview;
    @BindView(R.id.status_textview)
    TextView statusTextview;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.give_up)
    Button giveUp;
    @BindView(R.id.add_a_danhao)
    Button addADanhao;
    @BindView(R.id.start_delivery)
    Button startDelivery;
    @BindView(R.id.qiandao)
    Button qiandao;
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;

    // private HomeAdapter mAdapter;

    private boolean mAtTop = true;

    private int mScrollTotal;

    public static DeliverFragment newInstance() {
        Bundle args = new Bundle();
        DeliverFragment fragment = new DeliverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deliver, container, false);
        unbinder = ButterKnife.bind(this, view);
        //初始化标签
        tagGroup.setTags(new String[]{"全部", "待购", "待送达", "已购", "已送达"});
        initView(view);
        giveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                builder.setTitle("放弃接单");
                builder.setMessage("\n请小心确定，避免造成不必要的损失。");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(container.getContext(), "取消", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(container.getContext(), "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        startDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                builder.setTitle("确定开始送货");
                builder.setMessage("\n开始送货后，请及时定点签到，让买家了解宝贝的情况。");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(container.getContext(), "取消", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(container.getContext(), "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(container.getContext(), "应用后台定位中...", Toast.LENGTH_LONG).show();
                Toast.makeText(container.getContext(), "签到成功！", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);
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
        unbinder.unbind();
    }

}
