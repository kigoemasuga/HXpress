package com.example.android.hxpress.fragments.aboutme.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hxpress.R;
import com.example.android.hxpress.managers.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/6.
 */
public class AvatarFragment extends SupportFragment implements LoginFragment.OnLoginSuccessListener {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    Unbinder unbinder;
    View fragmentView;
    @BindView(R.id.user_name)
    TextView userName;

    public static AvatarFragment newInstance() {
        Bundle args = new Bundle();
        AvatarFragment fragment = new AvatarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.aboutme_fragment_avatar, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.loginedUser == null) {
                    start(LoginFragment.newInstance());
                } else {
                    start(LoginFragment.newInstance());
                    //展示头像
                    Log.v("Avatar", "已经登录了啦~");
                }
            }
        });
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLoginSuccess(String account) {
        userName.setText(account);
        Toast.makeText(fragmentView.getContext(), "登录成功, 用户名已经更改!", Toast.LENGTH_SHORT).show();
    }
}
