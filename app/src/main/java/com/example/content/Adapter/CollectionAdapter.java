package com.example.content.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.content.Entity.Video;
import com.example.content.R;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Video> mList;
    private List<Boolean> boolList = new ArrayList<>();
    public Boolean flag = false;

    private static final String TAG = "CollectionAdapter";
    public CollectionAdapter(Context context, ArrayList<Video> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_collection, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Video video = mList.get(position);
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.video_name.setText(video.getName());
        viewHolder.video_title.setText(video.getTitle());
        Glide.with(mContext).load(Uri.parse(video.getThumbnail())).into(viewHolder.video_img);
        if (flag) {
            viewHolder.btn_check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btn_check.setVisibility(View.GONE);
        }
        viewHolder.btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.btn_check.isChecked()) {
                    Log.i(TAG, "onClick: 我被点击了");
                    mList.get(position).isCheck = true;
                } else {
                    Log.i(TAG, "onClick: 我没有被点击");
                    mList.get(position).isCheck = false;
                    }
            }
        });
        viewHolder.btn_check.setChecked(video.isCheck);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
