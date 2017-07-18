package com.example.android.hxpress.aciticties;

import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.android.hxpress.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class WholeActivity extends AppCompatActivity {
    //底部菜单栏
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        intitialBottomBar();
    }

    private void intitialBottomBar(){
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //单击事件 menuItemId 是 R.menu.bottombar_menu 中 item 的 id
                switch (menuItemId){
                    //广场
                    case 0:
                        break;
                    //动态
                    case 1:
                        break;
                    //更多
                    case 2:
                        break;
                    //关于我
                    case 3:
                        break;
                }
            }
            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //重选事件，当前已经选择了这个，又点了这个tab。微博点击首页刷新页面
            }
        });
        // 当点击不同按钮的时候，设置不同的颜色
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, "#FF7B9C");
        mBottomBar.mapColorForTab(2, "#83CEFF");
        mBottomBar.mapColorForTab(3, "#FFEB3B");
    }
}
