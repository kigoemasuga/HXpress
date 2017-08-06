package com.example.android.hxpress.fragments.timeline.childpager;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.hxpress.R;
import com.example.android.hxpress.adapters.WantedThingAdapterInSquare;
import com.example.android.hxpress.fragments.timeline.DetailFragment;
import com.example.android.hxpress.listener.OnItemClickListener;
import com.example.android.hxpress.managers.UserManager;
import com.example.android.hxpress.models.ThingsWanted;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.datatype.BmobGeoPoint;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kigoe on 16/6/5.
 */
public class OtherPagerFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_TYPE = "arg_pos";
    public static int TYPE_WORLD = 1;
    public static int TYPE_BUY = 2;
    public static int TYPE_DELIVER = 3;
    Unbinder unbinder;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recy)
    RecyclerView recy;
    private WantedThingAdapterInSquare mAdapter;
    private boolean mAtTop = true;
    private int mScrollTotal;
    private int mType = TYPE_WORLD;
    private Context mContext;

    public static OtherPagerFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        OtherPagerFragment fragment = new OtherPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(ARG_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline_pager_other, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = container.getContext();
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);

        mAdapter = new WantedThingAdapterInSquare(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        recy.setLayoutManager(manager);
        recy.setAdapter(mAdapter);
        recy.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 这里的DetailFragment在flow包里
                // 这里是父Fragment启动,要注意 栈层级
                ((SupportFragment) getParentFragment()).start(DetailFragment.newInstance(mAdapter.getItem(position).getTitle()));
            }
        });

        recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        setHeader(recy);
        initView();
        return view;
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(mContext).inflate(R.layout.recycleview_header, view, false);
        ImageView imageView = (ImageView) header.findViewById(R.id.image_view);
        if (mType == TYPE_WORLD) {
            imageView.setImageResource(R.drawable.world);
        } else if (mType == TYPE_BUY) {
            imageView.setImageResource(R.drawable.buybuy);
        } else {
            imageView.setImageResource(R.drawable.need);
        }
        mAdapter.setHeaderView(header);
    }

    private void initView() {
        // Init Datas
        List<ThingsWanted> thingsWantedList = new ArrayList<>();
        // mAdapter.setDatas(thingsWantedList);
        ThingsWanted thingsWanted1, thingsWanted2, thingsWanted3, thingsWanted4, thingsWanted5, thingsWanted6, thingsWanted7;
        if (mType == TYPE_WORLD) {
            //世界
            thingsWanted1 = new ThingsWanted("美国DECT6.0无绳电话", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "中国制造");
            thingsWanted2 = new ThingsWanted("Costco卖的纯棉针织品", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                    ThingsWanted.WAIT_FOR_PAY_PRICE, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "美国");
            thingsWanted3 = new ThingsWanted("Canadiantire三折时的厨具", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=35&spn=0&di=34795071522&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935%2C1468584406&os=4066135448%2C2311833699&simid=3390486262%2C421558439&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bks52k7f_z%26e3Bv54AzdH3Fs52-s52fAzdH3Fncbcambb_z%26e3Bip4s&gsm=5a&rpstart=0&rpnum=0",
                    ThingsWanted.DEAL_WITHOUT_BUYED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "美国");
            thingsWanted4 = new ThingsWanted("Zippo打火机", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=167&spn=0&di=287432865072&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1775454729%2C1998449072&os=3423699580%2C3208303626&simid=4138668375%2C732456788&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&oriquery=&objurl=http%3A%2F%2Fimg.tradekey.com%2Fimages%2Fuploadedimages%2Foffers%2F3%2F2%2FA1660744-20080513094932.jpg&gsm=ab&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "美国");
            thingsWanted5 = new ThingsWanted("Costco儿童保健品", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_RECIEVE, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "日本");
            thingsWanted6 = new ThingsWanted("三洋eneloop充电电池", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.RECEIVED_WAIT_COMMENT, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "日本");
            thingsWanted7 = new ThingsWanted("FX参天眼药水", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.DEAL_DONE, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "日本");
        } else if (mType == TYPE_BUY) {
            //买买买
            thingsWanted1 = new ThingsWanted("求买小林退烧贴", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京超市发交大店");
            thingsWanted2 = new ThingsWanted("龙角散", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京超市发交大店");
            thingsWanted3 = new ThingsWanted("花王蒸汽眼罩", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=35&spn=0&di=34795071522&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935%2C1468584406&os=4066135448%2C2311833699&simid=3390486262%2C421558439&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bks52k7f_z%26e3Bv54AzdH3Fs52-s52fAzdH3Fncbcambb_z%26e3Bip4s&gsm=5a&rpstart=0&rpnum=0",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京超市发交大店");
            thingsWanted4 = new ThingsWanted("Royce生巧克力", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=167&spn=0&di=287432865072&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1775454729%2C1998449072&os=3423699580%2C3208303626&simid=4138668375%2C732456788&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&oriquery=&objurl=http%3A%2F%2Fimg.tradekey.com%2Fimages%2Fuploadedimages%2Foffers%2F3%2F2%2FA1660744-20080513094932.jpg&gsm=ab&rpstart=0&rpnum=0",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京屈臣氏");
            thingsWanted5 = new ThingsWanted("白色恋人", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京屈臣氏");
            thingsWanted6 = new ThingsWanted("富士山下的特质制造金币", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京屈臣氏");
            thingsWanted7 = new ThingsWanted("Gakki", "來個人幫我賣一賣吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.POST_WANTED, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "日本");
        } else {
            //随手送
            thingsWanted1 = new ThingsWanted("求买小林退烧贴", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京超市发交大店");
            thingsWanted2 = new ThingsWanted("龙角散", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935,1468584406&os=4066135448,2311833699&simid=3390486262,421558439&pn=35&rn=1&di=34795071522&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=5a&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1500609565119^3_1583X778%1",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京超市发交大店");
            thingsWanted3 = new ThingsWanted("北京伏特加", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=35&spn=0&di=34795071522&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=314959935%2C1468584406&os=4066135448%2C2311833699&simid=3390486262%2C421558439&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpublic.blogbus.com%2Fimages%2Fnews%2F2009%2F02-28%2Fmeishi1.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bks52k7f_z%26e3Bv54AzdH3Fs52-s52fAzdH3Fncbcambb_z%26e3Bip4s&gsm=5a&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京超市发交大店");
            thingsWanted4 = new ThingsWanted("Бабаевский巧克力", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=butterknife%E6%89%BE%E4%B8%8D%E5%88%B0id&step_word=&hs=0&pn=167&spn=0&di=287432865072&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1775454729%2C1998449072&os=3423699580%2C3208303626&simid=4138668375%2C732456788&adpicid=0&lpn=0&ln=1213&fr=&fmq=1500609550393_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=100&height=100&face=undefined&ist=&jit=&cg=&oriquery=&objurl=http%3A%2F%2Fimg.tradekey.com%2Fimages%2Fuploadedimages%2Foffers%2F3%2F2%2FA1660744-20080513094932.jpg&gsm=ab&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京屈臣氏");
            thingsWanted5 = new ThingsWanted("香港中村藤吉的甜品", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京屈臣氏");
            thingsWanted6 = new ThingsWanted("macbook", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "北京屈臣氏");
            thingsWanted7 = new ThingsWanted("Gakki", "给买主送一下吧~",
                    "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%AB%98%E6%B8%85%E5%8A%A8%E6%BC%AB&step_word=&hs=0&pn=0&spn=0&di=75167095960&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2632338153%2C1640220652&os=2897694834%2C2240667731&simid=4117334604%2C715298864&adpicid=0&lpn=0&ln=1973&fr=&fmq=1462357247335_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150514%2F21036787_181947848862_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8d90ama8_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                    ThingsWanted.WAIT_FOR_EXPRESS, UserManager.loginedUser, new BmobGeoPoint(20, 20), "北京交通大學", "井磚", new BmobGeoPoint(20, 20), "日本");
        }
        thingsWantedList.add(thingsWanted1);
        thingsWantedList.add(thingsWanted2);
        thingsWantedList.add(thingsWanted3);
        thingsWantedList.add(thingsWanted4);
        thingsWantedList.add(thingsWanted5);
        thingsWantedList.add(thingsWanted6);
        thingsWantedList.add(thingsWanted7);
        // mAdapter.setDatas(thingsWantedList);
        mAdapter.addDatas(thingsWantedList);
        recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
