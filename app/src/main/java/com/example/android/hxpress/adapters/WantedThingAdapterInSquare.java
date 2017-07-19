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

    private List<ThingsWanted> mItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mClickListener;

    public WantedThingAdapterInSquare(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<ThingsWanted> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wanted, parent, false);
        mContext = parent.getContext();
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThingsWanted item = mItems.get(position);
        holder.titleTextview.setText(item.getTitle());
        holder.statusTextview.setText(item.statusCode2TextInSquare(item.getProgressNum()));
        holder.wantHelpBuyPeopleNumTextview.setText(item.getWant2HelpBuy().size());
        holder.wantHelpDeliveryPeopleNumTextview.setText(item.getWant2Helpdelivery().size());
        Glide.with(mContext).load(item.getTopImgUrls()).into(holder.thingPicImageview);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public ThingsWanted getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thing_pic_imageview)
        ImageView thingPicImageview;
        @BindView(R.id.where_can_buy)
        TextView whereCanBuy;
        @BindView(R.id.status_textview)
        TextView statusTextview;
        @BindView(R.id.want_help_buy_people_num_textview)
        TextView wantHelpBuyPeopleNumTextview;
        @BindView(R.id.want_help_delivery_people_num_textview)
        TextView wantHelpDeliveryPeopleNumTextview;
        @BindView(R.id.title_textview)
        TextView titleTextview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
