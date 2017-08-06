package com.example.android.hxpress.fragments.square.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.hxpress.R;
import com.example.android.hxpress.adapters.PhotoWallAdapter;
import com.example.android.hxpress.models.Images;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kigoe on 2017/8/7.
 */

public class PhotoFragment extends SupportFragment {
    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter adapter;

    public static PhotoFragment newInstance() {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        mPhotoWall = (GridView) view.findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(container.getContext(), 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        adapter.cancelAllTasks();
    }
}
