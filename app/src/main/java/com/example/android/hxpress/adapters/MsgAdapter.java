package com.example.android.hxpress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.hxpress.R;
import com.example.android.hxpress.listener.OnItemClickListener;
import com.example.android.hxpress.models.Msg;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.VH> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Msg> mItems = new ArrayList<>();

    private OnItemClickListener mClickListener;

    public MsgAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addMsg(Msg bean) {
        mItems.add(bean);
        notifyItemInserted(mItems.size() - 1);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_timeline_chatroom_item__msg, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(holder.getAdapterPosition(), v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Msg item = mItems.get(position);

        holder.tvMsg.setText(item.message);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    class VH extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvMsg;

        public VH(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
        }
    }
}
