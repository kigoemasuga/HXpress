package com.example.android.hxpress.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.hxpress.R;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by kigoe on 16/2/7.
 */
public class BaseBackFragment extends SwipeBackFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // _mActivity.onBackPressed();
                pop();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
    }
}
