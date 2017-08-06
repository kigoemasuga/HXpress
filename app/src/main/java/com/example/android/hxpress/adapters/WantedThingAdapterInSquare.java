package com.example.android.hxpress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.hxpress.R;
import com.example.android.hxpress.listener.OnItemClickListener;
import com.example.android.hxpress.models.ThingsWanted;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kigoe on 2017/7/20.
 */

public class WantedThingAdapterInSquare extends RecyclerView.Adapter<WantedThingAdapterInSquare.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private List<ThingsWanted> mItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private int type;
    private OnItemClickListener mClickListener;
    private View mHeaderView = null;
    private OnItemClickListener mListener;
    public WantedThingAdapterInSquare(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public void addDatas(List<ThingsWanted> datas) {
        mItems.addAll(datas);
        notifyDataSetChanged();
    }
    public void setDatas(List<ThingsWanted> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        View view = mInflater.inflate(R.layout.item_wanted_in_square, parent, false);
        mContext = view.getContext();
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        ThingsWanted item = mItems.get(pos);
        holder.titleTextview.setText(item.getTitle());
        holder.statusTextview.setText(item.statusCode2TextInSquare(item.getProgressNum()));
        //holder.helpBuyNum.setText(item.getWant2HelpBuy() == null ? 0 : item.getWant2HelpBuy().size());
        //holder.helpDeliveryNum.setText(item.getWant2Helpdelivery() == null ? 0 : item.getWant2Helpdelivery().size());
        //Glide.with(mContext).load(item.getTopImgUrls()).into(holder.thingPicImageview);
    }


    @Override
    public int getItemCount() {
        return mHeaderView == null ? mItems.size() : mItems.size() + 1;

    }

    public ThingsWanted getItem(int position) {
        return mItems.get(position);
    }

    /*public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thing_pic_imageview)
        ImageView thingPicImageview;
        @BindView(R.id.where_can_buy)
        TextView whereCanBuy;
        @BindView(R.id.title_textview)
        TextView titleTextview;
        @BindView(R.id.status_textview)
        TextView statusTextview;
        @BindView(R.id.help_buy_num)
        TextView helpBuyNum;
        @BindView(R.id.help_delivery_num)
        TextView helpDeliveryNum;

        ViewHolder(View view) {
            super(view);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, view);
        }
    }
}
