package com.example.android.hxpress.fragments.timeline.childpager;

import android.support.v7.widget.Toolbar;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.hxpress.R;
import com.example.android.hxpress.aciticties.WholeActivity;
import com.example.android.hxpress.adapters.ChatAdapter;
import com.example.android.hxpress.base.BaseMainFragment;
import com.example.android.hxpress.event.TabSelectedEvent;
import com.example.android.hxpress.listener.OnItemClickListener;
import com.example.android.hxpress.models.Chat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by kigoe on 16/6/30.
 */
public class ChatRoomFragment extends BaseMainFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private ChatAdapter mAdapter;

    public static ChatRoomFragment newInstance() {
        Bundle args = new Bundle();
        ChatRoomFragment fragment = new ChatRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline_chatroom, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

        EventBus.getDefault().register(this);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });
        mAdapter = new ChatAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                ((SupportFragment) getParentFragment()).start(MsgFragment.newInstance(mAdapter.getMsg(position)));
            }
        });

        List<Chat> chatList = initDatas();
        mAdapter.setDatas(chatList);
    }

    private List<Chat> initDatas() {
        List<Chat> msgList = new ArrayList<>();

        String[] name = new String[]{"小黄", "小李", "小张", "老王", "小马"};
        String[] chats = new String[]{"小黄的消息", "小李的消息", "小张的消息", "老王的消息", "小马的消息"};

        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 5);
            Chat chat = new Chat();
            chat.name = name[index];
            chat.message = chats[index];
            msgList.add(chat);
        }
        return msgList;
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != WholeActivity.FIRST) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressedSupport() {
        return true;
    }
}
